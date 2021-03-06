/*
 *  Copyright 1997-2011 teatrove.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.teatrove.teaservlet;

import java.beans.*;
import org.teatrove.teaservlet.util.NameValuePair;

/**
 * 
 * @author Brian S O'Neill
 */
public class FunctionInfo extends NameValuePair {
    private ApplicationInfo mApp;
    
    public FunctionInfo(MethodDescriptor method, ApplicationInfo provider) {
        super(method.getName(), method);
        mApp = provider;
    }

    public MethodDescriptor getDescriptor() {
        return (MethodDescriptor)getValue();
    }

    public ApplicationInfo getProvider() {
        return mApp;
    }

    public int compareTo(Object other) {
        String thisName = getName();
        String otherName = ((FunctionInfo)other).getName();

        if (thisName == null) {
            return otherName == null ? 0 : 1;
        }

        if (otherName == null) {
            return -1;
        }

        String thisPrefix, otherPrefix;
        int index = thisName.indexOf('$');
        if (index <= 0) {
            thisPrefix = "";
        }
        else {
            thisPrefix = thisName.substring(0, index);
            thisName = thisName.substring(index + 1);
        }

        index = otherName.indexOf('$');
        if (index <= 0) {
            otherPrefix = "";
        }
        else {
            otherPrefix = otherName.substring(0, index);
            otherName = otherName.substring(index + 1);
        }

        // Primary compare: name.
        int compare = thisName.compareToIgnoreCase(otherName);
        if (compare != 0) {
            return compare;
        }

        // Secondary compare: prefix.
        compare = thisPrefix.compareToIgnoreCase(otherPrefix);
        if (compare != 0) {
            return compare;
        }

        // Third order compare: parameter count.
        Class[] thisParams = getDescriptor().getMethod().getParameterTypes();
        Class[] otherParams = 
            ((FunctionInfo)other).getDescriptor().getMethod().getParameterTypes();
        
        if (thisParams.length < otherParams.length) {
            return -1;
        }
        else if (thisParams.length > otherParams.length) {
            return 1;
        }

        return 0;
    }
}
