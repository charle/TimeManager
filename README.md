#TimeManager

## 作用
- 管理时间
- 记录每天某个时间段所做的事
- 可以备份所有日记

## 功能页介绍
- 整体框架
    + 利用fragment+viewpager
- 首页
    + listview(注意上下拉到时会去刷新屏幕，空的要置空)

![](images/main.png)

- 新增日记页
    + 利用actionbar--menu菜单
    + 注意activity之间的通信

![](images/new_diary.png)

- 详情日记页
    + 利用actionbar--menu菜单
    + 注意activity之间的通信

![](images/diary_detail.png)

- 编辑日记页
    + 利用actionbar--menu菜单
    + 注意activity之间的通信

![](images/edit_diary.png)

- 编辑日记条目页
    + 利用actionbar--menu菜单
    + 注意activity之间的通信

![](images/edit_diary_item.png)

- 标签管理页
    + 系统默认的一些标签
    + 自己可以增加一些标签
    
![](images/tags.png)

- 分类详情页
    
![](images/detail_category.png)

- 分类标签编辑页
    
![](images/edit_category.png)

- 事件提醒页
    + 事件分成紧急，重要，暂缓，已处理
    
![](images/minder.png)


- 新建事件提醒页
    
![](images/create_minder.png)

- 事件提醒详情页
    + 可进删除与进编辑页操作
    
![](images/minder_detail.png)

- 设置页
    + 主要是个人信息管理
    + 进入密码管理
    + 导出日记
    
![](images/setting.png)

- 设置密码页

![](images/set_password.png)

- 编辑个性签名页

![](images/edit_sign.png)
