# LivePermissions
基于LiveData的权限申请库

详情可点击查看[基于Jetpack的LiveData动态权限申请](https://juejin.im/post/5e54ca08e51d4526cb161d2c)


## 使用方法 ##
### 1.添加依赖

```
implementation 'com.ftd.livepermissions:livepermissions:1.0.0'
```
### 2.添加代码

```kotlin
//申请权限

LivePermissions(this).request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).observe(this, Observer {
                when (it) {
                    is PermissionResult.Grant -> {  //权限允许
                        Toast.makeText(this, "Grant", Toast.LENGTH_SHORT).show()
                    }
                    is PermissionResult.Rationale -> {  //权限拒绝
                        it.permissions.forEach {s->                      
                            println("Rationale:${s}")//被拒绝的权限
                        }
                        Toast.makeText(this, "Rationale", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is PermissionResult.Deny -> {   //权限拒绝，且勾选了不再询问
                        it.permissions.forEach {s->
                            println("deny:${s}")//被拒绝的权限
                        }
                        Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show()
                    }
                }
            })

```
