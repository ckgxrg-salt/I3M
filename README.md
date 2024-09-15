# I3M, Improved IoT In Minecraft
这是一个用于测试校园数字孪生系统功能的简易Mod  
目前实现有SynkLamp同步灯, Xtinguisher灭火器X型, SuperconductPost超导桩
分别对应灯光检测，灭火器检测与充电桩检测

### 测试方法
运行游戏客户端：
```
./gradlew runClient
```
或者Windows下
```
./gradlew.bat runClient
```

在Minecraft中创建新世界，并通过如下指令(按/打开指令界面)获取方块: 
```
/give @s i3m:synk_lamp //同步灯
/give @s i3m:xtinguisher //灭火器X型
/give @s i3m:superconduct_post //超导桩
```

将方块放置后，其默认处于下线状态，通过该指令获取调试棒: 
```
/give @s minecraft:debug_stick
```
通过手持调试棒左击方块切换属性，右击调整值，将connected值调整为true，即可让方块“上线”

指向它并通过如下指令: 
(坐标可以通过在输入完/data merge block后指向方块按三次Tab补全，或按F3查看)
```
/data merge block <x坐标> <y坐标> <z坐标> {mqtt_value:"<数值>"}
```
其中同步灯的取值范围为0-150 
灭火器X型为0-100-600 
超导桩为0-6 
