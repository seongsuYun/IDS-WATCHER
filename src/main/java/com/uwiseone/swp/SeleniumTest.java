package com.uwiseone.swp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {

    private WebDriver driver;
    private WebElement webElement;

	String domain = "sr.daewoong.co.kr";
    
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:/Users/user/Documents/selenium/chromedriver_win32/chromedriver.exe";
    
    private List<String> resultMessage = new ArrayList<String>();
    
	public SeleniumTest() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.setHeadless(true);
        
        driver = new ChromeDriver(options);
	}
	
    public static void main(String[] args) {
        SeleniumTest selTest = new SeleniumTest();
        selTest.executeTestProcess();
    }
    
    public void executeTestProcess() {
    	try {
    		
    		if(checkBearworldAppLogin("https://"+this.domain+"/wiseone-sso-server/jsp/login/dw/login-web.jsp?serviceParam=121.78.169.136&servicePort=80&serviceSeq=12")) {
    			resultMessage.add("fail : bearworld(136) main page");
    			System.out.println("fail : bearworld(136) main page");
    		} else {
    			resultMessage.add("success : bearworld(136) main page");
    			System.out.println("success : bearworld(136) main page");
    		};
    		
    		if(checkBearworldAppLogin("https://"+this.domain+"/wiseone-sso-server/jsp/login/dw/login-web.jsp?serviceParam=121.78.169.140&servicePort=80&serviceSeq=11")) {
    			resultMessage.add("fail : bearworld(140) main page");
    			System.out.println("fail : bearworld(140) main page");
    		} else {
    			resultMessage.add("success : bearworld(140) main page");
    			System.out.println("success : bearworld(140) main page");
    		};
    		
    		if(checkBearworldAppAlf("http://121.78.169.136:8280/alfresco/faces/jsp/dashboards/container.jsp")) {
    			resultMessage.add("fail : bearworld(136) alfresco main");
    			System.out.println("fail : bearworld(136) alfresco main");
    		} else {
    			resultMessage.add("success : bearworld(136) alfresco main");
    			System.out.println("success : bearworld(136) alfresco main");
    		};
    		
    		if(checkBearworldAppAlf("http://121.78.169.140:8280/alfresco/faces/jsp/dashboards/container.jsp")) {
    			resultMessage.add("fail : bearworld(140) alfresco main");
    			System.out.println("fail : bearworld(140) alfresco main");
    		} else {
    			resultMessage.add("success : bearworld(140) alfresco main");
    			System.out.println("success : bearworld(140) alfresco main");
    		};

//    		if(checkBearworldAppECM("http://121.78.169.193:8080/alfresco/faces/jsp/dashboards/container.jsp")) {
//    			resultMessage.add("fail : bearworld(193) alfresco ECM main");
//    			System.out.println("fail : bearworld(193) alfresco ECM main");
//    		} else {
//    			resultMessage.add("success : bearworld(193) alfresco ECM main");
//    			System.out.println("success : bearworld(193) alfresco ECM main");
//    		};
    		
    		checkBearworldAppExternal("https://www.yougetsignal.com/tools/open-ports", "121.78.169.136", "80");
    		checkBearworldAppExternal("https://www.yougetsignal.com/tools/open-ports", "121.78.169.136", "8280");
    		checkBearworldAppExternal("https://www.yougetsignal.com/tools/open-ports", "121.78.169.140", "80");
    		checkBearworldAppExternal("https://www.yougetsignal.com/tools/open-ports", "121.78.169.140", "443");

    		checkBearworldAppCPU136();
    		checkBearworldAppCPU140();
    		
    		StringBuilder message = new StringBuilder();
			for(String m : resultMessage) {
				message.append("\n").append(m);
			}
			
			String completeMessage = message.toString();
			
			System.out.println("==== monitoring result ====");
			System.out.println(completeMessage);
			
			if(completeMessage.contains("fail")) {
				WorksBotV2Test test = new WorksBotV2Test();
				
				String requestUrl = "https://apis.worksmobile.com/daewoongGroupware/message/sendMessage/v2";
				String requestBody = "{\"botno\": 5933,\"accountId\": \"ssyun@idstrust.com\",\"content\": {\"type\": \"text\",\"text\": \""+completeMessage+"\"}}";
				
				// 봇 메세지 전송
				test.sendBotMessage(requestUrl, requestBody);
			}
			
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void checkBearworldAppCPU136() throws InterruptedException {
    	Thread.sleep(1000);
    	
    	try {
        	driver.get("http://itas.daewoong.co.kr/account/Login");
        	driver.findElement(By.id("userId")).sendKeys("groupware");
        	driver.findElement(By.id("password")).sendKeys("rmfnqdnpdj1!");
        	Thread.sleep(1000);        	
        	webElement = driver.findElement(By.className("btn-secondery"));
        	webElement.click();
        	
        	Thread.sleep(3000);
        	
        	driver.get("http://itas.daewoong.co.kr/management/resourcedetail/10837");
        	Thread.sleep(1500);
        	webElement = driver.findElement(By.id("realtimeZone"));
        	
        	String[] resourceRate = new String[2];
        	List<WebElement> list =  webElement.findElements(By.className("progress"));        	
        	for(int i=0; i<list.size(); i++) {
        		resourceRate[i] = list.get(i).getAttribute("data-percent");
        	}
        	
        	System.out.println("bearworld(136) : " + Arrays.toString(resourceRate));
        	
        	float cpu = Float.parseFloat(resourceRate[0].replace("%", ""));
        	float mem = Float.parseFloat(resourceRate[1].replace("%", ""));
        	
        	if(cpu > 50) {
        		resultMessage.add("fail : bearworld(136) system cpu high");
        	} else {
        		resultMessage.add("success : bearworld(136) system cpu");
        	}
        	
        	if(mem > 85) {
        		resultMessage.add("fail : bearworld(136) system mem high");
        	} else {
        		resultMessage.add("success : bearworld(136) system mem");
        	}
        	
    	} catch(Exception e) {
    	}
    } 
    
    private void checkBearworldAppCPU140() throws InterruptedException {
    	Thread.sleep(1000);
    	try {
//        	driver.get("http://itas.daewoong.co.kr/account/Login");
//        	driver.findElement(By.id("userId")).sendKeys("groupware");
//        	driver.findElement(By.id("password")).sendKeys("rmfnqdnpdj1!");
//        	Thread.sleep(1000);        	
//        	webElement = driver.findElement(By.className("btn-secondery"));
//        	webElement.click();
//        	
//        	Thread.sleep(3000);
        	
        	driver.get("http://itas.daewoong.co.kr/management/resourcedetail/8963");
        	Thread.sleep(1500);
        	webElement = driver.findElement(By.id("realtimeZone"));
        	
        	String[] resourceRate = new String[2];
        	List<WebElement> list =  webElement.findElements(By.className("progress"));        	
        	for(int i=0; i<list.size(); i++) {
        		resourceRate[i] = list.get(i).getAttribute("data-percent");
        	}
        	
        	System.out.println("bearworld(140) : " + Arrays.toString(resourceRate));
        	
        	float cpu = Float.parseFloat(resourceRate[0].replace("%", ""));
        	float mem = Float.parseFloat(resourceRate[1].replace("%", ""));
        	
        	if(cpu > 50) {
        		resultMessage.add("fail : bearworld(140) system cpu high");
        	} else {
        		resultMessage.add("success : bearworld(140) system cpu");
        	}
        	
        	if(mem > 85) {
        		resultMessage.add("fail : bearworld(140) system mem high");
        	} else {
        		resultMessage.add("success : bearworld(140) system mem");
        	}
        	
    	} catch(Exception e) {
    	}
    	
    	driver.close();
    } 
    
    private void checkBearworldAppExternal(String url, String target, String port) {
    	try {
    		Thread.sleep(1000);
        	driver.get(url);
        	
        	webElement = driver.findElement(By.id("remoteAddress"));
        	webElement.clear();
        	webElement.sendKeys(target);
        	
        	webElement = driver.findElement(By.id("portNumber"));
        	webElement.clear();
        	webElement.sendKeys(port);
        	
        	webElement = driver.findElement(By.className("myButton"));
        	webElement.click();
        	
        	Thread.sleep(3000);
        	
        	webElement = driver.findElement(By.id("statusDescription"));
        	if(webElement.getText().contains("is open on")) {
        		resultMessage.add("success : bearworld "+target+" external port "+port);
        		System.out.println("success : bearworld "+target+" external port "+port);	
        	} else {
        		resultMessage.add("fail : bearworld "+target+" external port "+port);
        		System.out.println("fail : bearworld "+target+" external port "+port);
        	}
    	} catch(Exception e) {
    		
    	}
    }
    
    private boolean checkBearworldAppECM(String url) throws InterruptedException {
    	Thread.sleep(1000);
    	boolean isFail = false;
    	
    	try {
        	driver.get(url);
        	
        	webElement = driver.findElement(By.id("login"));
        	webElement.click();
        	
        	Thread.sleep(500);
        	
        	driver.findElement(By.id("loginForm:user-name")).sendKeys("admin");
        	driver.findElement(By.id("loginForm:user-password")).sendKeys("admin");
        	Thread.sleep(1000);
        	driver.findElement(By.id("loginForm:submit")).click();
        	Thread.sleep(1000);
        	
        	webElement = driver.findElement(By.id("logout"));
        	if(webElement == null) {
        		return true;
        	}
        	
    	} catch(Exception e) {
    		isFail = true; 
    	}
    	
    	return isFail;
    } 
    
    private boolean checkBearworldAppAlf(String url) throws InterruptedException {
    	Thread.sleep(1000);
    	boolean isFail = false;
    	
    	try {
        	driver.get(url);
        	webElement = driver.findElement(By.id("login"));
        	webElement.click();
        	
        	Thread.sleep(500);
        	
        	driver.findElement(By.id("loginForm:user-name")).sendKeys("admin");
        	driver.findElement(By.id("loginForm:user-password")).sendKeys("admin");
        	Thread.sleep(1000);
        	driver.findElement(By.id("loginForm:submit")).click();
        	Thread.sleep(1000);
        	
        	webElement = driver.findElement(By.id("logout"));
        	if(webElement == null) {
        		return true;
        	}
    	} catch(Exception e) {
    		isFail = true; 
    	}
    	
    	return isFail;
    } 
    
    private boolean checkBearworldAppLogin(String url) throws InterruptedException {
    	Thread.sleep(1000);
    	boolean isFail = false;
    	
    	try {
        	driver.get(url);
            webElement = driver.findElement(By.id("j_username"));
            webElement.sendKeys("ssyun");
            
            webElement = driver.findElement(By.id("j_password"));
            webElement.sendKeys("B0s1j93966@!");
            
            webElement = driver.findElement(By.className("loginBtn"));
            webElement.click();
            
            Thread.sleep(500);
            
            webElement = driver.findElement(By.id("poDBoard"));
            String dataUrl = webElement.getAttribute("data-data-url");
            if(dataUrl == null) {
            	return true;
            }
            
            // 로그인 후 팝업창 닫기
            String parentWindow= driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();
            for(String curWindow : allWindows){
                driver.switchTo().window(curWindow);
            }
            
            driver.switchTo().window(parentWindow);
    	} catch(Exception e) {
    		isFail = true; 
    	}
    	
    	return isFail;
    }
}