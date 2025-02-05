package es.dipucr.ownCloud;
/* ownCloud Android client application
 *   Copyright (C) 2012  Bartek Przybylski
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

public class WebdavUtils {
	public static final Logger LOGGER =  Logger.getLogger(WebdavUtils.class);

    public static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    
    private static final SimpleDateFormat DATETIME_FORMATS[] = {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US),
            new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US) };

    public static String prepareXmlForPropFind() {
        String ret = "<?xml version=\"1.0\" ?><D:propfind xmlns:D=\"DAV:\"><D:allprop/></D:propfind>";
        return ret;
    }

    public static String prepareXmlForPatch() {
        return "<?xml version=\"1.0\" ?><D:propertyupdate xmlns:D=\"DAV:\"></D:propertyupdate>";
    }

    public static Date parseResponseDate(String date) {
        Date returnDate = null;
        for (int i = 0; i < DATETIME_FORMATS.length; ++i) {
            try {
                returnDate = DATETIME_FORMATS[i].parse(date);                
            } catch (ParseException e) {
            	LOGGER.error(e.getMessage(), e);
            }
            if(returnDate!=null)            
            	return returnDate;
        }
        return null;
    }

    /**
     * Encodes a path according to URI RFC 2396. 
     * 
     * If the received path doesn't start with "/", the method adds it.
     * 
     * @param remoteFilePath    Path
     * @return                  Encoded path according to RFC 2396, always starting with "/"
     */
    public static String encodePath(String remoteFilePath) {
        if (!remoteFilePath.startsWith("/"))
        	remoteFilePath = "/" + remoteFilePath;
        return remoteFilePath;
    }
    
    
    
    public static void main(String args[]){
    	
    	//Test
    	
    	String arg= "Tue, 07 Aug 2018 08:07:04 GMT";
    	    	    	
    	Date test = WebdavUtils.parseResponseDate(arg);
    	
    	System.out.print(test);
    	
    }
    
}
