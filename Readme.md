# omnetpp自动测试脚本

功能：
- [x] java类转omnetpp.ini文件
- [x] tsnkit自动测试脚本
- [x] tsnkit门控转omnetpp.ini文件
- [x] omnetpp自动测试脚本

## Usage

``` bash
cd [your omnetpp path]
git clone -recursive git@github.com:shxuuer/omnet-tools.git
```
!important, you should add "recursive" command to clone tsnkit project!

Change run_tsnkit.sh and run_opp.sh script to fit your project, and ch-
ange network topo with Main.java. You should config network.ned in omn-
etpp and topo.csv in tsnkit_data by yourself, which is the same topo as
Main.java.
