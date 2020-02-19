/*
 * Copyright 2018 Soojeong Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.myapplication.coldpod.network;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "image", strict = false)
public class ItemImage {

    @Attribute(name = "href", required = false)
    private String mHref;

    public String getItemImageHref() {
        return mHref;
    }

    public void setItemImageHref(String href) {
        mHref = href;
    }
}