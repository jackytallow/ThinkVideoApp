## 从零开始打造一款简单的视频类APP
基于轻量级视频播放内核框架ijkplayer,打造一款视频点播的APP

### 功能简介
- 点播功能
（支持多种频道，电视剧，综艺，动画等）
- 点播核心功能
1. 播放状态机，播放流程控制)
2. 搜狐，乐视数据解析)
3. 超清，高清，标清视频渲染)
- 直播功能
1. 真实视频源数据（央视直播，卫视直播）
2. VLC工具
- 直播核心功能
1. 节目直播原理
2. 节目直播协议（RTMP,FLV,HLS）
3. 直播源采集
4. m3u8工具
- UI界面开发
1. 节目直播列表开发
2. 节目直播逻辑实现
3. 节目直播填坑

### ijkplayer框架介绍(B站开发的)
- ijkplayer作为播放内核，支持MP4，flv，ts，mkv，rmvb
- ijkplayer基于FFmpeg扩展，支持MediaCodec硬解码
- ijkplayer支持ExoPlayer播放DRM版权视频以及DASH协议
- ijkplayer进行编译(ndk)，移植到Android平台，ijkplayer集成，源码修改


### 项目技术栈
#### 高级控件
- ViewPager
- RecyclerView
- DrawerLayout
- SwipeRefreshLayout
- NavigationView
- Toolbar
- 自定义RecyclerView（下拉刷新，上拉加载）
- 自定义Viewpager指示器(通用指示器设计)
- 自定义GridView

#### 主流开源库
- okhttp
- gson
- glide
-  superindicator


###  项目功能开发
#### 启动，引导页功能实现
- 图片+延迟2s跳转
思路：
1. 启动页面是一张图，并且延时2s
2. 读取SharedPreferences文件，决定是否第一次启动
3. 如果首次启动，就跳引导页面，否则直接2s后跳转到首页
- viewpager+小圆点
思路：
1. viepager实例化三个图，每个页面显示三个原点
2. 通过监听pageChangeListener，来显示小点的颜色
3. 最后一张引导页，加一张按钮图片，点击可以跳转到首页
4. 跳转到首页同时，更新是否第一次进入的状态

## 侧滑菜单实现
- NavigationView + DrawerLayout
思路：
1. NavigationView：左边滑出的部分是那一部分的布局，分两部分，上面一部分叫做headerlayout，下面那些是menu
2. BlogFraagment:setWebClient帮助WebView处理javaScript的对话框，网站图标，网站title，加载进度

## 首页实现
1. 无限轮播边缘循环部分逻辑，自动轮播，使用handler，post5s，需要轮播的时候点击进行轮播
2. 圆点对象持有viewpager，根据viewpager的count得到自身个数
3. viewpager的adapter用一个包装类，可以设置是否边缘循环以及第一个和最后一个是否要缓存起来

## 列表页思路
1. Activity中嵌套Viewpager，ViewPager再组合Fragment
2. Viewpager有自定义指示器
3. Frragment页面有下拉刷新，下拉加载更多
