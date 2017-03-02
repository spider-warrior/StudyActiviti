/**
 * Created by wxsk100 on 2016/11/15.
 */
//常量
var UNDEFINED = 'undefined';
var STRING = 'string';
var NUMBER_CHARS = ['1','2','3','4','5','6','7','8','9','0'];

//请求类型
var GET = "GET";
var POST = "POST";
var PUT = "PUT";
var DELETE = "DELETE";

//code
//服务器内部异常
var SERVER_INTERNAL_EXCEPTION_CODE = "500";
//406
var REQUEST_NOT_ALLOWED = "405";
//404
var RESOURCE_NOT_FOUND_EXCEPTION_CODE = "404";
//400
var REQUEST_PARAM_ERROR = "400";


//选择器
function $(id) {
    if (typeof id == UNDEFINED || typeof id != STRING || id == null) {
        return null;
    }
    if (id.startwith('#')) {
        return document.getElementById(id.substring(1));
    }
    else if (id.startwith(".")) {
        return document.getElementsByClassName(id.substring(1));
    }
    else {
        var eles = document.getElementsByTagName(id);
        if (eles.length > 0) {
            return eles[0];
        }
        return null;
    }
}

$.getUri = function () {
    return document.location.pathname;
}

//函数
if (!JSON) {
    JSON = {};
}
function indexOf(arr, val) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] === val) {
            return i;
        }
    }
    return -1;
}
if (!JSON.parse) {
    JSON.parse = function (json) {
        return eval('(' + json + ')');
    };
}
if (!typeof String.prototype.startwith) {

    String.prototype.startwith = function (prefix) {
        if (this == prefix) {
            return true;
        }
        if (typeof prefix != STRING) {
            return false;
        }
        if (this.length < prefix.length) {
            return false;
        }
        return this.substring(0, prefix.length) === prefix;
    }
}

String.prototype.startwith = function (prefix) {
    if (this == prefix) {
        return true;
    }
    if (typeof prefix != STRING) {
        return false;
    }
    if (this.length < prefix.length) {
        return false;
    }
    return this.substring(0, prefix.length) === prefix;
}

if (!typeof String.prototype.trim) {
    String.prototype.trim = function (str) {
        //header " "
        for (var i = 0; i < str.length; i++) {
            if (str[i] != " ") {
                if (i != 0) {
                    str = str.substring(i);
                }
                break;
            }
        }
        for (var i = str.length - 1; i > -1; i--) {
            if (str[i] != " ") {
                if (i != str.length - 1) {
                    str = str.substring(0, i + 1);
                }
                break;
            }
        }
        return str;
    }
}

function parseSimpleObject(object) {
    var type = typeof object;
    if (type == "string" || type == "function") {
        return "\"" + object.toString().replace("\"", "\\\"") + "\"";
    }

    if (type == "number" || type == "boolean") {
        return object.toString();
    }

    if (type == "undefined") {
        return "undefined";
    }

    return "\"" + object.toString().replace("\"", "\\\"") + "\"";
}
if (!JSON.stringify) {
    JSON.stringify = function stringify(object) {
        var type = typeof object;

        //如果是简单类型，则直接返回简单类型的结果
        if (indexOf(simpleTypes, type) > -1) {
            return parseSimpleObject(object);
        }

        //数组对象的
        if (object instanceof Array) {
            var len = object.length;
            var resArr = [];
            for (var i = 0; i < len; i++) {
                var itemType = typeof object[i];
                if (indexOf(simpleTypes, itemType) > -1) {

                    //undefined特殊处理，数组中变成null
                    if (itemType != "undefined") {
                        resArr.push(parseSimpleObject(object[i]));
                    } else {
                        resArr.push("null");
                    }

                } else {
                    //递归处理JS数组中的复杂元素
                    resArr.push(stringify(object[i]));
                }
            }

            return "[" + resArr.join(",") + "]";
        }

        //普通object对象
        if (object instanceof Object) {
            if (object == null) {
                return "null";
                bodyBytes
            }

            var resArr = [];

            for (var name in object) {
                var itemType = typeof object[name];
                if (indexOf(simpleTypes, itemType) > -1) {
                    //undefined特殊处理，object中不编码
                    if (itemType != "undefined") {
                        resArr.push("\"" + name + "\":" + parseSimpleObject(object[name]));
                    }
                } else {
                    resArr.push("\"" + name + "\":" + stringify(object[name]));
                }
            }

            return "{" + resArr.join(",") + "}";
        }
    }
}

function createXmlHttpRequest() {
    if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();

        if (xmlHttp.overrideMimeType) {
            xmlHttp.overrideMimeType("text/xml");
        }
    }
    else if (window.ActiveXObject) {
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    if (!xmlHttp) {
        window.alert("你的浏览器不支持创建XMLhttpRequest对象");
    }
    return xmlHttp;
}

function executeRequest(url, param, method, callback) {

    var xmlHttp = createXmlHttpRequest();
    xmlHttp.open(method, url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlHttp.setRequestHeader("x-requested-with", "XMLHttpRequest");
    xmlHttp.send(JSON.stringify(param));
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4)
            if (xmlHttp.status == 200) {
                callback(eval("(" + xmlHttp.responseText + ")"));
            }
    };
}

$.redirect404 = function () {
    document.location.href = "/404";
};

$.redirect500 = function () {
    document.location.href = "/500";
};

$.redirectRoot = function() {
    document.location.href = "/";
};

$.alertError = function(message) {
    alert(message);
};

$.getUserIndexUrl = function () {
    return "/user/" + username;
};
$.randomNumberChars = function(len) {
    if (typeof len == UNDEFINED || len == null) {
        len = 8;
    }
    var result = '';
    for (var i=0; i<len; i++) {
        result += Math.floor(Math.random()*10);
    }
    return result;
};

$.bindHtml = function (bindConfig) {
    for (var key in bindConfig) {
        var ele = $("#" + key);
        if (typeof ele != 'undefined' && ele != null) {
            ele.innerHTML = bindConfig[key];
        }
    }
};


$.registerEvent = function(id, event, handler){
    var dom = $("#" + id);
    if (dom) {
        if (dom.attachEvent) {
            // Microsoft
            dom.attachEvent (event, handler);
        } else if (dom.addEventListener) {
            // Mozilla: DOM level 2
            dom.addEventListener (event, handler, false);
        }
    }
};
// stop listening to event
$.unregisterEvent = function(id, event, handler) {
    var dom = $("#" + id);
    if (dom) {
        if (dom.detachEvent) {
            // Microsoft
            dom.detachEvent (event, handler);
        } else if (dom.removeEventListener) {
            // Mozilla: DOM level 2
            dom.removeEventListener (event, handler, false);
        }
    }
};



$.createTr = function() {
    return document.createElement("tr");
};

$.createTd = function() {
    return document.createElement("td");
};

$.createTdWithText = function(text) {
    var td = document.createElement("td");
    td.innerText = text;
    return td;
};

$.createTdWithHtml = function(text) {
    var td = document.createElement("td");
    td.innerHTML = text;
    return td;
};

$.createAnchor = function(setting) {
    var config = setting || {};
    var a = document.createElement("a");
    if (config.innerText) {
        a.innerText = config.innerText;
    }
    if (config.href) {
        a.href = config.href;
    }
    else {
        a.href = "javascript:void(0);";
    }
    if (config.click) {
        a.onclick = config.click;
    }
    return a;
};

$.createTextElement = function(content) {
    return document.createTextNode(content);
}

$.setCookie = function(c_name,value,expiredays)
{
    var exdate=new Date();
    exdate.setDate(exdate.getDate()+expiredays);
    document.cookie=c_name+ "=" +encodeURI(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
};


$.getCookie = function(c_name)
{
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(c_name + "=");
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if (c_end==-1) c_end=document.cookie.length;
            return decodeURI(document.cookie.substring(c_start,c_end))
        }
    }
    return "";
};


