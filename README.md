# Eyepetizer-Jetpack（未完成）

## 开眼视频的MVVM版本，采用viewmodel+kotlin协程+paging+viewpager2+navigation,当然里面踩了不少坑

## 希望这个项目能给渴望学习的你带来一点收获：

### 1.正确的使用协程处理异步请求 

kotlin的协程教程我参考的是这篇文章：

[https://kaixue.io/tag/kotlin-coroutines/]()

### 2.viewmodel activity和application不同作用域正确使用方法，可以实现整体toolbar无缝切换，当然这个只是简单的做法。

viewmodel作为最重要的数据持有者，作为使用者的我们，必须得明白viewmodel的职能边界。

简化原理：我们在初始化viewmodel的时候，需要传入两个参数，其中第一个参数就是ViewModelStoreOwner，

这是一个接口类，说道这里，你应该明白了吧，viewmdel的作用域由ViewModelStoreOwner来进行限定。

### 3.谷歌在Android Jetpack组件中提供了paging这个分页库组件供我们加载分页数据，

官方文档：[https://developer.android.com/topic/libraries/architecture/paging](https://developer.android.com/topic/libraries/architecture/paging)

利用

```
DiffUtil.ItemCallback
```

和

```
DataSource.invalidate
```

来进行数据是否加载的判定，应用情景如下：

当用户进行刷新操作，返回数据与我们第一次加载数据一致，recyclerview不进行notify操作，虽然没有太大影响，在能优化的地方进行优化，是一个程序员的素养。

### 4.完美解决viewpager2和横向滚动recyclerview的滑动冲突。

当然网上有很多这样的文章，在这里主要是重温一遍手势分发机制，请务必记住以下结论，都是改bug，改出来的血泪史：

#### 1.touch事件在onInterceptTouchEvent方法中的传递由父ViewGroup到子ViewGroup，在onTouchEvent方法中传递则相反。

#### 2.onInterceptTouchEvent方法和onTouchEvent方法的返回值为true都代表消费了事件，反之则为false

#### 3.onInterceptTouchEvent消费事件表示将事件直接传递给ViewGroup自身的onTouchEvent事件，后续事件不再经过onInterceptTouchEvent方法；不消费事件则表示将事件传递给子View处理

#### 4.onTouchEvent消费事件表示不再向上传递，后续事件继续传递给该View的onTouchEvent方法；不消费事件则表示将事件传递给父ViewGroup，后续事件不再传递给该View(该View是ViewGroup时onInterceptTouchEvent方法也不再收到后续事件)

### 5.glide中CustomTarget来进行图片的尺寸修改，自适应图片的宽高比例

所在代码：GlideUtils



## 不足之处：

### 首页推荐的复杂布局采用的是recycleview嵌套多个recycleview完成，准备改成阿里的Tangram-Android

## 踩过的坑：

1.最开始的时候，我是Navigation+bottom的设计，但是Navigation在底部切换再返回，就会重新执行oncereatview的方法，用户体验及其不好，虽然可以更改，但是需要复制大片源码，侵入太高，就pass掉了。

2.在实现首页复杂布局中，最开始的设计是利用Recyclerview的spansizeup来完成，但是研究过开眼api之后，与业务需要完全不符，从而pass，recycleview嵌套多个recycleview其实也不符合，最近尝试使用阿里的Tangram看能不能解决这个问题。

## 最后感谢开眼api：

每日推荐：[ http://baobab.kaiyanapp.com/api/v5/index/tab/allRec]()

发现更多：[ http://baobab.kaiyanapp.com/api/v7/index/tab/discovery]()

日报精选：[http://baobab.kaiyanapp.com/api/v2/feed?date=1587949200000&num=1]()
