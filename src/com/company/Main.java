package com.company;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", "/Users/spencerheffley/Driver/chromedriver");
        driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor)driver;
//
//        driver.get("https://www.google.com/");
//        driver.findElement(By.name("q")).sendKeys("lol limewire");
//        try {
//            Thread.sleep(250);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        driver.findElement(By.name("btnI")).click();
//        driver.findElement(By.tagName("body")).sendKeys(Keys.SPACE);

        driver.get("https://en.wikipedia.org/wiki/Special:Random");
        ArrayList<String> pagesVisited = new ArrayList<>();
        ArrayList<String> pageLog = new ArrayList<>();
        int counter = 0;
        String lastPage = driver.getTitle();
        System.out.println(lastPage);
        while (!driver.getTitle().contains("Spongebob")) {
            boolean tooMany = false;
            if (counter > 10) {
                tooMany = true;
                for (int i = 0; i < pageLog.size() - 1; i++) {
                    if (pageLog.get(i) != pageLog.get(i + 1)) {
                        tooMany = false;
                    }
                }
            }
            if (driver.getTitle().contains(":") || driver.getCurrentUrl().contains("/media/") ||
                    driver.getTitle().contains("User list") || !driver.getCurrentUrl().contains("en.wikipedia.org") ||
                    driver.getTitle().contains("Reset password") || driver.getTitle().contains("Editing") ||
                    driver.getCurrentUrl().contains("/map/") || driver.getTitle().contains("Wikimedia") ||
                    pagesVisited.contains(driver.getTitle()) || tooMany) {
                js.executeScript("window.history.go(-1)");
            }
            try {
                if (!driver.getTitle().equals(lastPage)) {
                    System.out.println(driver.getTitle());
                    lastPage = driver.getTitle();
                    if (!pagesVisited.contains(lastPage)) {
                        pagesVisited.add(lastPage);
                    }
                }
                if (pageLog.size() > 5) {
                    pageLog.remove(0);
                }
                pageLog.add(driver.getTitle());
                WebElement bodyText = driver.findElement(By.id("mw-content-text"));
                ArrayList<WebElement> linksOnPage = new ArrayList<>();
                for (WebElement paragraph : bodyText.findElements(By.tagName("p"))) {
                    for (WebElement link : paragraph.findElements(By.tagName("a"))) {
                        linksOnPage.add(link);
                    }
                }
                int random = (int)(Math.random() * linksOnPage.size());
                linksOnPage.get(random).click();
                counter++;
            } catch (Exception e) {
                js.executeScript("window.history.go(-1)");
            }
//            boolean noClick = true;
//            for (WebElement link : linksOnPage) {
//                if (!links.contains(link.getText()) && link.getText().length() > 0 && !link.getAttribute("href").contains("#/map/0")) {
//                    links.add(link.getText());
//                    link.click();
//                    noClick = false;
//                    counter++;
//                    break;
//                }
//            }
//            if (noClick) {
//                js.executeScript("window.history.go(-1)");
//            }
        }
        System.out.println(counter);
    }
}
