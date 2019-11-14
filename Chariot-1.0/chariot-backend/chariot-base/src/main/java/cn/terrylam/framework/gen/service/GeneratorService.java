package cn.terrylam.framework.gen.service;

import cn.terrylam.framework.gen.vo.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TerryLam 2019/7/31
 * @version 1.0
 * @description
 **/
public class GeneratorService {
    protected static Configuration cfg;
    private static String packageName;
    private static String author;
    private static String path;
    private static String prefix="";
    private static List<String> tableNames;

    private final String ENTITY_FTL = "pojo.ftl";
    private final String SERVICE_FTL = "service.ftl";
    private final String DAO_FTL = "dao.ftl";
    private final String CONTROLLER_FTL = "controller.ftl";
    private final String FORM_FTL = "form.ftl";
    private final String INDEX_FTL = "index.ftl";
    private final String EDIT_FTL = "editform.ftl";

    private List<Table> allTable = new ArrayList<>();

    public static GeneratorService build(){
        return new GeneratorService();
    }

    private GeneratorService(){
        packageName = "cn.pconline.xxx";
        author = "pconline";
        path = "/data/gen/";
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/gen/");
    }

    public GeneratorService setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public GeneratorService setAuthor(String author) {
        this.author = author;
        return this;
    }

    public GeneratorService setPath(String path) {
        this.path = path;
        return this;
    }

    public GeneratorService setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public GeneratorService setTableNames(List<String> tableNames) {
        GeneratorService.tableNames = tableNames;
        return this;
    }

    public void gen() throws IOException {
        if (CollectionUtils.isEmpty(tableNames)) {
            getAll();
        } else {
            getByNames();
        }
        genPojo();
        genService();
        genDao();
        genForm();
        genController();
        genIndex();
        genEdit();
    }

    private void getAll(){
        DataService dataService = new DataService(prefix);
        allTable = dataService.getAllTable();
    }

    private void getByNames(){
        DataService dataService = new DataService(prefix);
        allTable = dataService.getByName(tableNames);
    }

    private void genPojo() throws IOException {
        writeFile(ENTITY_FTL,path+"/entity/","{{tableClassName}}.java");
    }

    private void genService( ) throws IOException {
        writeFile(SERVICE_FTL,path+"/service/","{{tableClassName}}Service.java");
    }

    private void genDao() throws IOException {
        writeFile(DAO_FTL,path+"/dao/","{{tableClassName}}Dao.java");
    }

    private void genForm() throws IOException {
        writeFile(FORM_FTL,path+"/form/","{{tableClassName}}PageForm.java");
    }

    private void genController() throws IOException {
        writeFile(CONTROLLER_FTL,path+"/controller/","{{tableClassName}}Controller.java");
    }

    private void genIndex() throws IOException {
        writeFile(INDEX_FTL,path+"/template/{{upperClassName}}/","index.html");
    }

    private void genEdit() throws IOException {
        writeFile(EDIT_FTL,path+"/template/{{upperClassName}}/","form.html");
    }

    private void writeFile(String tpl,String dirPath,String fileName) throws IOException {
        Template template = cfg.getTemplate(tpl);
        OutputStreamWriter writer=null;

        Map valueMap2 = new HashMap();
        valueMap2.put("package",packageName);
        valueMap2.put("author",author);
        for(Table table:allTable) {
            String dirPathNew = dirPath.replace("{{upperClassName}}",table.getUpperCaseClassName());
            File file = new File(dirPathNew);
            if(!file.exists()){
                file.mkdirs();
            }
            valueMap2.put("table",table);
            try{
                String filePath = fileName.replace("{{tableClassName}}", table.getClassName());
                writer = new FileWriter(dirPathNew + filePath);
                template.process(valueMap2,writer);
                writer.flush();
            }catch (TemplateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                writer.close();
            }
        }
    }
}
