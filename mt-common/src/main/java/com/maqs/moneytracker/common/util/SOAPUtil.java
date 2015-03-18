package com.maqs.moneytracker.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.maqs.moneytracker.common.logging.Logger;
import com.maqs.moneytracker.common.service.exception.ServiceException;
import com.maqs.moneytracker.common.service.runtimeexception.MissingResourceException;
import com.maqs.moneytracker.common.service.runtimeexception.SOAPRequestException;

/**
 * Utility class to make SOAP calls.
 *
 * @author maqbool.ahmed
 *
 */
public final class SOAPUtil {

	private static Logger logger = Logger.getLogger(SOAPUtil.class);

	private SOAPUtil() {

	}

	/**
	 * Local constants to help the SOAP calls.
	 *
	 */
	private static class Constants {

		private static final String SOAP_REQUEST_ENVELOP = "soap.envelope";

		private static final String SOAP_SERVICE_NS_START = "soap.ns.ser.startTag";

		private static final String SOAP_SERVICE_NS_END = "soap.ns.ser.endTag";

		private static final String CONFIG_PROPERTIES_PATH = "/com/maqs/moneytracker/common/config.properties";

		private static final String SOAP_ACTION = "SOAPAction";

		private static final String COMMA_STRING = ", ";
	}

	private static final String XML = "./test-jaxb.xml";

	/**
	 * Properties loaded from Constants.CONFIG_PROPERTIES_PATH
	 */
	private static Properties configProperties;

	static {
		InputStream stream = Util.class
				.getResourceAsStream(Constants.CONFIG_PROPERTIES_PATH);

		if (stream != null) {
			configProperties = new Properties();
			try {
				configProperties.load(stream);
				stream.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			throw new MissingResourceException(Constants.CONFIG_PROPERTIES_PATH + " does not exists.");
		}
	}

	/**
	 * Sends the soapRequest to the url and returns the response.
	 *
	 * @param soapUrl
	 * @param soapRequestXml
	 * @return soap response
	 * @throws ServiceException
	 */
	public static String sendSOAPRequest(String soapUrl, String soapRequestXml) {
		if (soapUrl == null || soapRequestXml == null)
			throw new NullPointerException(
					"soapUrl or soapRequestXml cannot be null...");

		StringBuilder builder = new StringBuilder();
		BufferedReader rd = null;
		try {
			java.net.URL url = new java.net.URL(soapUrl);
			java.net.URLConnection conn = url.openConnection();
			conn.setRequestProperty(Constants.SOAP_ACTION, soapUrl);
			conn.setDoOutput(true);
			conn.setConnectTimeout(2000);
			// Send the request
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(soapRequestXml);
			wr.flush();

			// Read the response
			rd = new BufferedReader(new java.io.InputStreamReader(
					conn.getInputStream()));

			String line;
			while ((line = rd.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			String msg = e.getMessage();
			SOAPRequestException ex = new SOAPRequestException(msg);
			logger.error(msg, ex);
			throw ex;
		} finally {
			try {
				if (rd != null)
					rd.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}

	/**
	 * Generates the requestXml by appending the methodName namespace & the
	 * requestParams.
	 *
	 * @param methodName
	 * @param requestParams
	 * @return
	 * @throws JAXBException
	 */
	public static String generateSoapRequestXml(String methodName,
			Object... requestParams) {
		SOAPInput input = new SOAPInput();
		return generateSoapRequestXml(input, methodName, requestParams);
	}

	/**
	 * Generates the SOAP request based on the arguments.
	 * @param input defines the url, envelopeKey, ...
	 * @param methodName what method to call?
	 * @param requestParams method arguments
	 * @return xml data
	 */
	public static String generateSoapRequestXml(SOAPInput input,
			String methodName, Object... requestParams) {
		String methodStartTagProp = configProperties.getProperty(input
				.getMethodStartTagKey());
		String methodEndTagProp = configProperties.getProperty(input
				.getMethodEndTagKey());
		String soapEnvelopProp = configProperties.getProperty(input
				.getEnvelopKey());

		if (methodStartTagProp == null || methodEndTagProp == null
				|| soapEnvelopProp == null) {
			String msg = "soap request cannot be made as the following properties are not available in "
				+ input.getConfigFileLocation() + "\n" +
					(methodStartTagProp == null ? input.getMethodStartTagKey() + Constants.COMMA_STRING : "") +
					(methodEndTagProp == null ? input.getMethodEndTagKey() + Constants.COMMA_STRING : "") +
					(soapEnvelopProp == null ? input.getEnvelopKey() : "");
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}

		String methodStartTag = MessageFormat.format(methodStartTagProp,
				methodName);
		String methodEndTag = MessageFormat
				.format(methodEndTagProp, methodName);
		StringBuilder requestParamsXml = new StringBuilder();
		for (Object param : requestParams) {
			String objectXml;
			objectXml = getXmlAsString(param);
			requestParamsXml.append(objectXml);
		}

		String soapRequestXml = MessageFormat.format(soapEnvelopProp,
				methodStartTag, requestParamsXml, methodEndTag);
		return soapRequestXml;
	}

	/**
	 * create xmlAsString from java object
	 *
	 * @param object
	 * @return a String
	 * @throws JAXBException
	 */
	public static String getXmlAsString(Object o) {
		InputStream fis = getXmlAsStream(o);
		StringBuilder buffer = new StringBuilder();
		try {
			int i = -1;
			while ((i = fis.read()) != -1) {
				buffer.append((char) i);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		String xmlString = buffer.toString();
		xmlString = xmlString.substring(xmlString.indexOf('\n') + 1);
		return xmlString;
	}

	/**
	 * create xmlAsStream from java object
	 *
	 * @param object
	 * @return a String
	 * @throws JAXBException
	 */
	public static InputStream getXmlAsStream(Object o) {
		FileOutputStream fos = null;
		FileInputStream fis = null;

		try {
			JAXBContext context = JAXBContext.newInstance(o.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			fos = new FileOutputStream(XML);
			m.marshal(o, fos);

			fis = new FileInputStream(XML);
		} catch (Exception ioe) {
			throw new SOAPRequestException(ioe);
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return fis;
	}

	/**
	 * Class to determine the SOAP input.
	 *
	 * @author maqbool.ahmed
	 *
	 */
	public static class SOAPInput {

		private final String envelopKey;

		private final String methodStartTagKey;

		private final String methodEndTagKey;

		private String configFileLocation;

		private String soapUrl;

		public SOAPInput() {
			this.envelopKey = Constants.SOAP_REQUEST_ENVELOP;
			this.methodStartTagKey = Constants.SOAP_SERVICE_NS_START;
			this.methodEndTagKey = Constants.SOAP_SERVICE_NS_END;
			this.configFileLocation = Constants.CONFIG_PROPERTIES_PATH;
		}

		public SOAPInput(String envelopKey, String methodStartTagKey,
				String methodEndTagKey) {
			this.envelopKey = envelopKey;
			this.methodStartTagKey = methodStartTagKey;
			this.methodEndTagKey = methodEndTagKey;
		}

		public String getSoapUrl() {
			return soapUrl;
		}

		public void setSoapUrl(String soapUrl) {
			this.soapUrl = soapUrl;
		}

		public String getConfigFileLocation() {
			return configFileLocation;
		}

		public String getEnvelopKey() {
			return envelopKey;
		}

		public String getMethodStartTagKey() {
			return methodStartTagKey;
		}

		public String getMethodEndTagKey() {
			return methodEndTagKey;
		}
	}

}
