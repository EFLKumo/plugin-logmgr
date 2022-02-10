# `LogMgr` 插件

### 简介
为 [`ja-netfilter`](https://github.com/ja-netfilter/ja-netfilter) 开发的一款插件，
可以按照用户需求删除过期的日志。

### 使用方法
1. 从 [发布页面](https://github.com/RayGicEFL/plugin-logmgr/releases) 下载`.jar`文件，放入`ja-netfilter`的插件目录  
2. 在`ja-netfilter`配置文件目录下新建`logmgr.conf`，输入：
```
[LogMgr]
EQUAL,Days->5
```
这代表`ja-netfilter`会删除最后修改时间在五天以前的日志文件。  
你也可以这么写：

```
[LogMgr]
EQUAL,Nums->5
```
这代表`ja-netfilter`只会保留最新五个日志文件。
~~狗~~zhile只建议选择第一个模式：
[![HtfIBV.png](https://s4.ax1x.com/2022/02/10/HtfIBV.png)](https://imgtu.com/i/HtfIBV)

3. 愉快地使用`ja-netfilter`~

## English version

### About
A plugin developed for [`ja-netfilter`](https://github.com/ja-netfilter/ja-netfilter),
it will delete expired log files as you want.  

### How to use
1. Download `.jar` file via [release page](https://github.com/RayGicEFL/plugin-logmgr/releases).
2. Create `logmgr.conf` file under config dir of `ja-netfilter` and write:
```
[LogMgr]
EQUAL,Days->5
```
which means `ja-netfilter` will delete log files that last modified before 5 days.
You can also write:
```
[LogMgr]
EQUAL,Nums->5
```
which means `ja-netfilter` will keep 5 latest log files.  
Notice that the first method is recommended from zhile, author of `ja-netfilter`.
