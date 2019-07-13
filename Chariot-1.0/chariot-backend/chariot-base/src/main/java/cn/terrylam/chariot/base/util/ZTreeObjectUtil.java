package cn.terrylam.chariot.base.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wuzhaoqiang
 * @version 1.0
 * @className ZTreeObjectUtil
 * @description  ztree 转换工具类
 * @date 2019/6/11
 **/
public class ZTreeObjectUtil {

    private static ZTreeObjectUtil zTreeObjectUtil;
    public static ZTreeObjectUtil getInstance (){
        if (zTreeObjectUtil == null) {
            zTreeObjectUtil=new ZTreeObjectUtil();
        }
        return zTreeObjectUtil;
    }

    /**
     * 根据simpleData的List进行转换成ztree结构对象
     * @param z 默认的父级记录
     * @param datas
     * @return
     */
    public Ztree getByRoot( Ztree z,List<SimpleData> datas){
        z.setChildren(recursionChildren(z.getId(),datas));
        return z;
    }

    /**
     * 递归查找子记录
     * @param pid
     * @param datas
     * @return
     */
    public  List<Ztree> recursionChildren(String pid,List<SimpleData> datas){
        List<Ztree> children=new ArrayList<>();
        Iterator<SimpleData> iR=datas.iterator();
        while(iR.hasNext()){
            SimpleData s= iR.next();
            if(pid.equals(s.getpId())){
                Ztree z = toZtree(s);
                List<Ztree> zl=recursionChildren(z.getId(),datas);
                if(zl!=null&&!zl.isEmpty()){
                    z.setChildren(zl);
                }
                children.add(z);
            }
        }
        return children;
    }

    /**
     *  simpleData 转 ztree
     * @param s
     * @return
     */
    public  Ztree toZtree(SimpleData s){
        return new Ztree(s.id,s.name,s.checked,s.open);
    }

    /**
     * 内部类，simpleData
     */
    public class SimpleData{
        private String id;
        private String name;
        private boolean checked;
        private boolean open;
        private String pId;

        public SimpleData(String id, String name, boolean checked, boolean open, String pId) {
            this.id = id;
            this.name = name;
            this.checked = checked;
            this.open = open;
            this.pId = pId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public String getpId() {
            return pId;
        }

        public void setpId(String pId) {
            this.pId = pId;
        }
    }

    /**
     * 内部类，ztree
     */
    public class Ztree{
        private String id;
        private String name;
        private boolean checked;
        private boolean open;
        private List<Ztree> children;

        public Ztree(String id, String name, boolean checked, boolean open) {
            this.id = id;
            this.name = name;
            this.checked = checked;
            this.open = open;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public List<Ztree> getChildren() {
            return children;
        }

        public void setChildren(List<Ztree> children) {
            this.children = children;
        }
    }
}
