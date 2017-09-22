# ExtendEditText

支持一键清除，内容匹配的输入框

## 运行效果
![](http://bmob-cdn-9267.b0.upaiyun.com/2017/09/22/63f6524140eefc97808ef4a59a8bcc12.gif)

## 如何使用

打开工程根目录build.gradle
```
allprojects {
    repositories {
        jcenter()
        //添加内容
        maven { url 'https://jitpack.io' }
    }
}
```
打开依赖Module中的build.gradle
```
compile 'com.github.samuelhuahui:ExtendEditText:1.0.1'
```

打开使用的布局
```
    <com.huaye.extendedittext.ExtendEditText
        android:layout_width="260dp"
        android:layout_height="60dp"/>

    <com.huaye.extendedittext.ExtendEditText
        android:layout_width="260dp"
        android:layout_height="60dp"
        android:layout_marginTop="30dp"
        app:extend_regex="^[1]\\d{10}$"
        android:hint="请输入手机号"/>
        
    <com.huaye.extendedittext.ExtendEditText
            android:layout_width="260dp"
            android:layout_height="60dp"
            android:layout_marginTop="30dp"
            app:extend_regex="^[1]\\d{10}$" // 设置匹配正则
            app:extend_icon_ok=""  //设置匹配图标
            app:extend_icon_clear="" //设置清图片
            android:hint="请输入手机号"/>    
```
## 说明：
1. 比设置extend_regex值，则右侧一直显示为圆差
2. 更换右侧图片也可以通过updateRightDrawable()
3. setOnMatchListener匹配回调
4. setOnClearListener清空回调，如果匹配了，则清空失效
5. 统一监听setOnTextChangedListener，为了避免多处添加监听，这里重新添加字符改变后的回调。

## License
```The MIT License (MIT)
   
   Copyright (c) 2016 samuelhuahui
   
   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:
   
   The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
 ```
