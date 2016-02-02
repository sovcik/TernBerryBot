/*
 * @(#) ResourceBundleUTF8.java
 *
 * Tern Tangible Programming System
 * Copyright (C) 2016 Jozef Sovcik
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tern.ui;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Jozef on 02.02.2016.
 */
public class ResourceBundleUTF8 {
    // workaround for handling UTF8 resources

    static ResourceBundle bundle;

    public ResourceBundleUTF8(String res, Locale loc){
        bundle = ResourceBundle.getBundle(res, loc);
    }

    public String getString(String key)  {
        String s = bundle.getString(key);
        try {
            s = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println(e);
        }
        return s;
    }

}
