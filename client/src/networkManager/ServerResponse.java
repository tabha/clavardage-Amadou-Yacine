/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkManager;

/**
 *
 * @author amadou
 */

public class ServerResponse {
	
	public static final int TEST_SERVER =0;
    public static final int USER_CONNECTION = 1;
    public static final int USER_DISCONNECTION = 2;
    public static final int USER_CREATE = 3;
    public static final int NO_ERROR = 20;
    public static final int NO_ACTION = 21;
    public static final int NO_USER_DATA = 22;
    public static final int INVALID_JSON =23;
    public static final int INVALID_ENTRY =24;
	
	private int error;
    private String dataFormat;
    private String data;
    public ServerResponse(int code,String dataFormat){
        this.error = code;
        this.dataFormat = dataFormat;
        this.data = null;
    }    
    public int getError(){
        return this.error;
    }
    public String getDataFormat(){
        return this.dataFormat;
    }
    public String getData(){
        return this.data;
    }
    public void setData(String data){
        this.data = data;
    }
    public void setError(int err){
        this.error = err;
    }
    public void setDataFormat(String format){
        this.dataFormat = format;
    }
}
