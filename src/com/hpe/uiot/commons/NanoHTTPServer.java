package com.hpe.uiot.commons;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

public class NanoHTTPServer extends NanoHTTPD {
	
	String message = "";

    public NanoHTTPServer() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    public static void main(String[] args) {
        try {
            new NanoHTTPServer();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
    	System.out.println("Headers: " + session.getHeaders());
    	System.out.println("Params: " + session.getQueryParameterString());
        String msg = "<html><body><h1>Hello server</h1>\n";
       
        return newFixedLengthResponse(msg + "</body></html>\n");
    }
    
    public String getMessage(){
    	return message;
    }
}