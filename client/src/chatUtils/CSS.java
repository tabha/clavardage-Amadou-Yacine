/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatUtils;

/**
 *
 * @author amadou
 */
public class CSS {
    public static String conversationView = "<style type='text/css'>"
                          + ".message-sent{margin:3px 5px 3px 50px;padding:0 5px 5px 5px;background:#FF8075;color:white;font-size:14pt;}"
                          + ".message-received{margin:3px 50px 3px 5px;padding:0 5px 5px 5px;background:#eeeeee;color:black;font-size:14pt;}"
                          + ".date-sent{font-size:11pt;color:white;}"
                          + ".date-received{font-size:11pt;color:black;}"
                          + ".user-sent{font-size:11pt;color:#888888;margin:3px 0 0 55px;}"
                          + ".user-received{font-size:11pt;color:#888888;margin:3px 0 0 10px;}"
                          + "</style>";   

    public static String CONTAINER = ".container {\n" +
                                          "  width: 190px;\n" +
                                          //"  background-color: #f1f1f1;\n" +
                                          //"  border-radius: 5px;\n" +
                                          "  padding: 5px;\n" +
                                          "  margin: 2px 0;\n" +
                                      "}";
    public static String DARKER = ".darker {\n" +
                                      "  border-color: #ccc;\n" +
                                      "  background-color: #ddd;\n" +
                                  "}";
    public static String IMG_CONTAINER = ".container img {\n" +
                                    "  float: left;\n" +
                                    "  max-width: 60px;\n" +
                                    "  width: 100%;\n" +
                                    "  margin-right: 20px;\n" +
                                    "  border-radius: 50%;\n" +
                                "}";
    
    public static String IMG_RIGHT=".container img.right {\n" +
                                "  float: right;\n" +
                                "  margin-left: 20px;\n" +
                                "  margin-right:0;\n" +
                                "}";
    public static String TIME=".time-right {\n" +
                                "  float: right;\n" +
                                "  color: #aaa;\n" +
                                "}\n" +
                                ".time-left {\n" +
                                "  float: left;\n" +
                                "  color: #999;\n" +
                                "}";
    public static String LEFT=".right {\n" +
                                "  float: right;\n" +
                                "  color: #aaa;\n" +
                                "}\n" +
                                ".time-left {\n" +
                                "  float: left;\n" +
                                "  color: #999;\n" +
                                "}";
    public static String STYLE="<style type='text/css'>"
                                +CONTAINER
                                +DARKER
                                +IMG_CONTAINER
                                +IMG_RIGHT
                                +TIME
                                +"</style>";

}   