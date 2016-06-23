package com.lovver.ssdbj.core;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.lovver.ssdbj.Logger;
import com.lovver.ssdbj.core.impl.SSDBConnection;
import com.lovver.ssdbj.exception.SSDBException;
import com.lovver.ssdbj.util.GT;

public class SSDBDriver {
	private static final Logger logger = new Logger();
	public static final int DEBUG = 2;
	public static final int INFO = 1;

	public SSDBConnection connect(Properties info)
			throws SSDBException {
		 long timeout = timeout(info);
         if (timeout <= 0)
             return makeConnection(info);

         ConnectThread ct = new ConnectThread(info);
         new Thread(ct, "SSDB Server connection thread").start();
         return ct.getResult(timeout);
	}
	
	private static SSDBConnection makeConnection(Properties props) throws SSDBException { 
        return new SSDBConnection(host(props), port(props), user(props), props);
	}
	

	private static long timeout(Properties props) {
		String timeout = props.getProperty("loginTimeout");
		if (timeout != null) {
			try {
				return (long) (Float.parseFloat(timeout) * 1000);
			} catch (NumberFormatException e) {
				// Log level isn't set yet, so this doesn't actually
				// get printed.
				logger.debug("Couldn't parse loginTimeout value: " + timeout);
			}
		}
		return DriverManager.getLoginTimeout() * 1000;
	}

	private static class ConnectThread implements Runnable {
		ConnectThread( Properties props) {
			this.props = props;
		}

		public void run() {
			SSDBConnection conn;
			Throwable error;

			try {
				conn = makeConnection(props);
				error = null;
			} catch (Throwable t) {
				conn = null;
				error = t;
			}

			synchronized (this) {
				if (abandoned) {
					if (conn != null) {
						try {
							conn.close();
						} catch (Exception e) {
						}
					}
				} else {
					result = conn;
					resultException = error;
					notify();
				}
			}
		}

		/**
		 * Get the connection result from this (assumed running) thread. If the
		 * timeout is reached without a result being available, a SQLException
		 * is thrown.
		 * 
		 * @param timeout
		 *            timeout in milliseconds
		 * @return the new connection, if successful
		 * @throws SQLException
		 *             if a connection error occurs or the timeout is reached
		 */
		public SSDBConnection getResult(long timeout) throws SSDBException {
			long expiry = System.currentTimeMillis() + timeout;
			synchronized (this) {
				while (true) {
					if (result != null)
						return result;

					if (resultException != null) {
						if (resultException instanceof SQLException) {
							resultException.fillInStackTrace();
							throw (SSDBException) resultException;
						} else {
							throw new SSDBException(
									GT.tr("Something unusual has occured to cause the driver to fail. Please report this exception."));
						}
					}

					long delay = expiry - System.currentTimeMillis();
					if (delay <= 0) {
						abandoned = true;
						throw new SSDBException(
								GT.tr("Connection attempt timed out."));
					}

					try {
						wait(delay);
					} catch (InterruptedException ie) {
						abandoned = true;
						throw new SSDBException(
								GT.tr("Interrupted while attempting to connect."));
					}
				}
			}
		}

		private final Properties props;
		private SSDBConnection result;
		private Throwable resultException;
		private boolean abandoned;
	}
	
	
	
	 /**
     * @return the hostname portion of the URL
     */
    private static String host(Properties props)
    {
//        return props.getProperty("SSDB_HOST", "localhost");
        return props.getProperty("SSDB_HOST", "localhost");
    }

    /**
     * @return the port number portion of the URL or the default if no port was specified
     */
    private static int port(Properties props)
    {
        return Integer.parseInt(props.getProperty("SSDB_PORT", "8888"));
    }

    /**
     * @return the username of the URL
     */
    private static String user(Properties props)
    {
        return props.getProperty("user", "");
    }

}
