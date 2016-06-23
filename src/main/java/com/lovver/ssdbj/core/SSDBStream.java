package com.lovver.ssdbj.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SSDBStream {
    private final String host;
    private final int port;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    
    
    public SSDBStream(String host, int port) throws IOException
    {
        this.host = host;
        this.port = port;
        changeSocket(new Socket(host, port));
    }
    
    public void changeSocket(Socket socket) throws IOException {
        this.socket = socket;
        socket.setTcpNoDelay(true);
        inputStream = new BufferedInputStream(socket.getInputStream(),8192);
        outputStream = new BufferedOutputStream(socket.getOutputStream(), 8192);
    }


    public void flush() throws IOException {
        outputStream.flush();
    }
    
    public void close() throws IOException    {
        outputStream.close();
        inputStream.close();
        socket.close();
    }
    
    public boolean isConnection(){
    	return socket.isConnected();
    }
    
    public boolean isBound(){
    	return socket.isBound();
    }
    /**
     * Send an array of bytes to the backend
     *
     * @param buf The array of bytes to be sent
     * @exception IOException if an I/O error occurs
     */
    public void Send(byte buf[]) throws IOException    {
        outputStream.write(buf);
    }
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket connection) {
		this.socket = connection;
	}
	

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}
}
