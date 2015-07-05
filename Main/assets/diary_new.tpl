<#list list as diary>
### ${diary.createt_time} ${diary.weather} ${diary.address}
<#list diary.list as item>
- ${item.category_name}
    + ${item.tag_name} ${item.begin_time} ${item.end_time}
    + ${item.note}
</#list>
</#list>


### ${createt_time} ${weather} ${address}
<items>
- ${category_name}
- ${tag_name} ${begin_time} ~ ${end_time}
- ${note}
</items>