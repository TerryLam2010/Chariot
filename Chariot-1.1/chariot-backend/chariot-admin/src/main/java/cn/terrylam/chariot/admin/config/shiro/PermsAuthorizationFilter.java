package cn.terrylam.chariot.admin.config.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PermsAuthorizationFilter extends AuthorizationFilter {
  
    @Override  
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {  
        Subject subject = getSubject(request, response);
        String[] permsArray = (String[]) mappedValue;
  
        if (permsArray == null || permsArray.length == 0) {
            return true;
        }   
  
        for(int i=0;i<permsArray.length;i++){
            if(subject.isPermitted(permsArray[i])){
                return true;    
            }    
        }    
        return false;    
    }  
  
}  