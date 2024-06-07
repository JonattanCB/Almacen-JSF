//<![CDATA[

function reload() {
    var cam = PF('pc');
    var deviceSelector = document.querySelector("select.photoCamDevices");
    var device = deviceSelector.value;
    cam.device = device;
    cam.reload();
}

function populateDeviceMenu() {
    var photoCam = PF('pc');
    var deviceSelector = document.querySelector("select");
    var availableDevices = photoCam.getAvailableDevices();
    if (availableDevices) {
        availableDevices.then(devices => devices.forEach(device => {
                var option = document.createElement("option");
                option.text = device.label;
                option.value = device.deviceId;
                deviceSelector.appendChild(option);
            })
        );
    } else {
        console.log("no devices available");
    }
}

populateDeviceMenu();

//]]>