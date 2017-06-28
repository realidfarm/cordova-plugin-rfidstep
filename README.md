# rfidstep Cordova Plugin

## 集成步骤

- 直接通过 url 安装：

        cordova plugin add https://github.com/realidfarm/cordova-plugin-rfid.git

- 或下载到本地安装：

        cordova plugin add Your_Plugin_Path

# API

## Methods

- [rfid.openDevice](#openDevice)
- [rfid.closeDevice](#closeDevice)
- [rfid.scanCycle](#scanCycle)

## openDevice

打开串口.

    rfid.openDevice();

## closeDevice

关闭串口.

    rfid.closeDevice();

## scanCycle

读取标签.

    rfid.scanCycle();


完整调用demo:
```js
    rfid.openDevice(function(data) {
        rfidscan = $interval(function() {
            rfid.scanCycle(function(data) {
                if (data) {
                    var tagid = data.split(":");
                    var strdata = tagid[1].split("|");
                    var item = {};
                    item.no = parseInt(strdata[0].substr(3));
                    item.step = strdata[1];
                    item.temp = parseInt(strdata[2])/100;
                    item.times = 1;
                    var flag = 0;
                    if($scope.items.length > 0){
                        for (var i = $scope.items.length - 1; i >= 0; i--) {
                            if($scope.items[i].no === item.no){
                                $scope.items[i].step = item.step;
                                $scope.items[i].temp = item.temp;
                                $scope.items[i].times  = $scope.items[i].times + 1;
                                flag = 1;
                            }
                        }
                    }
                    if(flag === 0){
                        $scope.items.push(item);
                        $scope.items.sort(by("no"));
                    }
                    $scope.$apply();
                }
            }, function(error) {
                alert("error:" + error);
            });
        }, 100);
    }, function(error) {
        alert("error:" + error);
    });
```