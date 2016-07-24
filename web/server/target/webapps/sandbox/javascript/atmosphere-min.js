(function(a,b){if(typeof define==="function"&&define.amd){define(b)
}else{a.atmosphere=b()
}}(this,function(){var c="2.2.11-javascript",a={},e,d=false,h=[],g=[],f=0,b=Object.prototype.hasOwnProperty;
a={onError:function(i){},onClose:function(i){},onOpen:function(i){},onReopen:function(i){},onMessage:function(i){},onReconnect:function(j,i){},onMessagePublished:function(i){},onTransportFailure:function(j,i){},onLocalMessage:function(i){},onFailureToReconnect:function(j,i){},onClientTimeout:function(i){},onOpenAfterResume:function(i){},WebsocketApiAdapter:function(j){var i,k;
j.onMessage=function(l){k.onmessage({data:l.responseBody})
};
j.onMessagePublished=function(l){k.onmessage({data:l.responseBody})
};
j.onOpen=function(l){k.onopen(l)
};
k={close:function(){i.close()
},send:function(l){i.push(l)
},onmessage:function(l){},onopen:function(l){},onclose:function(l){},onerror:function(l){}};
i=new a.subscribe(j);
return k
},AtmosphereRequest:function(ad){var q={timeout:300000,method:"GET",headers:{},contentType:"",callback:null,url:"",data:"",suspend:true,maxRequest:-1,reconnect:true,maxStreamingLength:10000000,lastIndex:0,logLevel:"info",requestCount:0,fallbackMethod:"GET",fallbackTransport:"streaming",transport:"long-polling",webSocketImpl:null,webSocketBinaryType:null,dispatchUrl:null,webSocketPathDelimiter:"@@",enableXDR:false,rewriteURL:false,attachHeadersAsQueryString:true,executeCallbackBeforeReconnect:false,readyState:0,withCredentials:false,trackMessageLength:false,messageDelimiter:"|",connectTimeout:-1,reconnectInterval:0,dropHeaders:true,uuid:0,async:true,shared:false,readResponsesHeaders:false,maxReconnectOnClose:5,enableProtocol:true,pollingInterval:0,heartbeat:{client:null,server:null},ackInterval:0,closeAsync:false,reconnectOnServerError:true,onError:function(aI){},onClose:function(aI){},onOpen:function(aI){},onMessage:function(aI){},onReopen:function(aJ,aI){},onReconnect:function(aJ,aI){},onMessagePublished:function(aI){},onTransportFailure:function(aJ,aI){},onLocalMessage:function(aI){},onFailureToReconnect:function(aJ,aI){},onClientTimeout:function(aI){},onOpenAfterResume:function(aI){}};
var ar={status:200,reasonPhrase:"OK",responseBody:"",messages:[],headers:[],state:"messageReceived",transport:"polling",error:null,request:null,partialMessage:"",errorHandled:false,closedByClientTimeout:false,ffTryingReconnect:false};
var aw=null;
var ag=null;
var A=null;
var o=null;
var Y=null;
var v=true;
var ay=0;
var K=0;
var ak=" ";
var aG=false;
var R=null;
var i;
var ax=null;
var S=a.util.now();
var z;
var aF;
ao(ad);
function aj(){v=true;
aG=false;
ay=0;
aw=null;
ag=null;
A=null;
o=null
}function V(){m();
aj()
}function x(aI){if(aI=="debug"){return q.logLevel==="debug"
}else{if(aI=="info"){return q.logLevel==="info"||q.logLevel==="debug"
}else{if(aI=="warn"){return q.logLevel==="warn"||q.logLevel==="info"||q.logLevel==="debug"
}else{if(aI=="error"){return q.logLevel==="error"||q.logLevel==="warn"||q.logLevel==="info"||q.logLevel==="debug"
}else{return false
}}}}}function aH(aI){if(x("debug")){a.util.debug(new Date()+" Atmosphere: "+aI)
}}function J(aJ,aI){if(ar.partialMessage===""&&(aI.transport==="streaming")&&(aJ.responseText.length>aI.maxStreamingLength)){return true
}return false
}function E(){if(q.enableProtocol&&!q.firstMessage){var aK="X-Atmosphere-Transport=close&X-Atmosphere-tracking-id="+q.uuid;
a.util.each(q.headers,function(aM,aO){var aN=a.util.isFunction(aO)?aO.call(this,q,q,ar):aO;
if(aN!=null){aK+="&"+encodeURIComponent(aM)+"="+encodeURIComponent(aN)
}});
var aI=q.url.replace(/([?&])_=[^&]*/,aK);
aI=aI+(aI===q.url?(/\?/.test(q.url)?"&":"?")+aK:"");
var aJ={connected:false};
var aL=new a.AtmosphereRequest(aJ);
aL.connectTimeout=q.connectTimeout;
aL.attachHeadersAsQueryString=false;
aL.dropHeaders=true;
aL.url=aI;
aL.contentType="text/plain";
aL.transport="polling";
aL.method="GET";
aL.data="";
aL.heartbeat=null;
if(q.enableXDR){aL.enableXDR=q.enableXDR
}aL.async=q.closeAsync;
am("",aL)
}}function I(){if(x("debug")){a.util.debug("Closing")
}aG=true;
if(q.reconnectId){clearTimeout(q.reconnectId);
delete q.reconnectId
}if(q.heartbeatTimer){clearTimeout(q.heartbeatTimer)
}q.reconnect=false;
ar.request=q;
ar.state="unsubscribe";
ar.responseBody="";
ar.status=408;
ar.partialMessage="";
ai();
E();
m()
}function m(){ar.partialMessage="";
if(q.id){clearTimeout(q.id)
}if(q.heartbeatTimer){clearTimeout(q.heartbeatTimer)
}if(q.reconnectId){clearTimeout(q.reconnectId);
delete q.reconnectId
}if(o!=null){o.close();
o=null
}if(Y!=null){Y.abort();
Y=null
}if(A!=null){A.abort();
A=null
}if(aw!=null){if(aw.canSendMessage){aw.close()
}aw=null
}if(ag!=null){ag.close();
ag=null
}ah()
}function ah(){if(i!=null){clearInterval(z);
document.cookie=aF+"=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
i.signal("close",{reason:"",heir:!aG?S:(i.get("children")||[])[0]});
i.close()
}if(ax!=null){ax.close()
}}function ao(aI){V();
q=a.util.extend(q,aI);
q.mrequest=q.reconnect;
if(!q.reconnect){q.reconnect=true
}}function au(){return q.webSocketImpl!=null||window.WebSocket||window.MozWebSocket
}function at(){var aJ=a.util.getAbsoluteURL(q.url.toLowerCase());
var aK=/^([\w\+\.\-]+:)(?:\/\/([^\/?#:]*)(?::(\d+))?)?/.exec(aJ);
var aI=!!(aK&&(aK[1]!=window.location.protocol||aK[2]!=window.location.hostname||(aK[3]||(aK[1]==="http:"?80:443))!=(window.location.port||(window.location.protocol==="http:"?80:443))));
return window.EventSource&&(!aI||!a.util.browser.safari||a.util.browser.vmajor>=7)
}function aa(){if(q.shared){ax=aD(q);
if(ax!=null){if(x("debug")){a.util.debug("Storage service available. All communication will be local")
}if(ax.open(q)){return
}}if(x("debug")){a.util.debug("No Storage service available.")
}ax=null
}q.firstMessage=f==0?true:false;
q.isOpen=false;
q.ctime=a.util.now();
if(q.uuid===0){q.uuid=f
}ar.closedByClientTimeout=false;
if(q.transport!=="websocket"&&q.transport!=="sse"){M(q)
}else{if(q.transport==="websocket"){if(!au()){az("Websocket is not supported, using request.fallbackTransport ("+q.fallbackTransport+")")
}else{af(false)
}}else{if(q.transport==="sse"){if(!at()){az("Server Side Events(SSE) is not supported, using request.fallbackTransport ("+q.fallbackTransport+")")
}else{D(false)
}}}}}function aD(aM){var aN,aL,aQ,aI="atmosphere-"+aM.url,aJ={storage:function(){function aR(aV){if(aV.key===aI&&aV.newValue){aK(aV.newValue)
}}if(!a.util.storage){return
}var aU=window.localStorage,aS=function(aV){return a.util.parseJSON(aU.getItem(aI+"-"+aV))
},aT=function(aV,aW){aU.setItem(aI+"-"+aV,a.util.stringifyJSON(aW))
};
return{init:function(){aT("children",aS("children").concat([S]));
a.util.on(window,"storage",aR);
return aS("opened")
},signal:function(aV,aW){aU.setItem(aI,a.util.stringifyJSON({target:"p",type:aV,data:aW}))
},close:function(){var aV=aS("children");
a.util.off(window,"storage",aR);
if(aV){if(aO(aV,aM.id)){aT("children",aV)
}}}}
},windowref:function(){var aR=window.open("",aI.replace(/\W/g,""));
if(!aR||aR.closed||!aR.callbacks){return
}return{init:function(){aR.callbacks.push(aK);
aR.children.push(S);
return aR.opened
},signal:function(aS,aT){if(!aR.closed&&aR.fire){aR.fire(a.util.stringifyJSON({target:"p",type:aS,data:aT}))
}},close:function(){if(!aQ){aO(aR.callbacks,aK);
aO(aR.children,S)
}}}
}};
function aO(aU,aT){var aR,aS=aU.length;
for(aR=0;
aR<aS;
aR++){if(aU[aR]===aT){aU.splice(aR,1)
}}return aS!==aU.length
}function aK(aR){var aT=a.util.parseJSON(aR),aS=aT.data;
if(aT.target==="c"){switch(aT.type){case"open":W("opening","local",q);
break;
case"close":if(!aQ){aQ=true;
if(aS.reason==="aborted"){I()
}else{if(aS.heir===S){aa()
}else{setTimeout(function(){aa()
},100)
}}}break;
case"message":l(aS,"messageReceived",200,aM.transport);
break;
case"localMessage":G(aS);
break
}}}function aP(){var aR=new RegExp("(?:^|; )("+encodeURIComponent(aI)+")=([^;]*)").exec(document.cookie);
if(aR){return a.util.parseJSON(decodeURIComponent(aR[2]))
}}aN=aP();
if(!aN||a.util.now()-aN.ts>1000){return
}aL=aJ.storage()||aJ.windowref();
if(!aL){return
}return{open:function(){var aR;
z=setInterval(function(){var aS=aN;
aN=aP();
if(!aN||aS.ts===aN.ts){aK(a.util.stringifyJSON({target:"c",type:"close",data:{reason:"error",heir:aS.heir}}))
}},1000);
aR=aL.init();
if(aR){setTimeout(function(){W("opening","local",aM)
},50)
}return aR
},send:function(aR){aL.signal("send",aR)
},localSend:function(aR){aL.signal("localSend",a.util.stringifyJSON({id:S,event:aR}))
},close:function(){if(!aG){clearInterval(z);
aL.signal("close");
aL.close()
}}}
}function aE(){var aJ,aI="atmosphere-"+q.url,aN={storage:function(){function aO(aQ){if(aQ.key===aI&&aQ.newValue){aK(aQ.newValue)
}}if(!a.util.storage){return
}var aP=window.localStorage;
return{init:function(){a.util.on(window,"storage",aO)
},signal:function(aQ,aR){aP.setItem(aI,a.util.stringifyJSON({target:"c",type:aQ,data:aR}))
},get:function(aQ){return a.util.parseJSON(aP.getItem(aI+"-"+aQ))
},set:function(aQ,aR){aP.setItem(aI+"-"+aQ,a.util.stringifyJSON(aR))
},close:function(){a.util.off(window,"storage",aO);
aP.removeItem(aI);
aP.removeItem(aI+"-opened");
aP.removeItem(aI+"-children")
}}
},windowref:function(){var aP=aI.replace(/\W/g,""),aO=document.getElementById(aP),aQ;
if(!aO){aO=document.createElement("div");
aO.id=aP;
aO.style.display="none";
aO.innerHTML='<iframe name="'+aP+'" />';
document.body.appendChild(aO)
}aQ=aO.firstChild.contentWindow;
return{init:function(){aQ.callbacks=[aK];
aQ.fire=function(aR){var aS;
for(aS=0;
aS<aQ.callbacks.length;
aS++){aQ.callbacks[aS](aR)
}}
},signal:function(aR,aS){if(!aQ.closed&&aQ.fire){aQ.fire(a.util.stringifyJSON({target:"c",type:aR,data:aS}))
}},get:function(aR){return !aQ.closed?aQ[aR]:null
},set:function(aR,aS){if(!aQ.closed){aQ[aR]=aS
}},close:function(){}}
}};
function aK(aO){var aQ=a.util.parseJSON(aO),aP=aQ.data;
if(aQ.target==="p"){switch(aQ.type){case"send":u(aP);
break;
case"localSend":G(aP);
break;
case"close":I();
break
}}}R=function aM(aO){aJ.signal("message",aO)
};
function aL(){document.cookie=aF+"="+encodeURIComponent(a.util.stringifyJSON({ts:a.util.now()+1,heir:(aJ.get("children")||[])[0]}))+"; path=/"
}aJ=aN.storage()||aN.windowref();
aJ.init();
if(x("debug")){a.util.debug("Installed StorageService "+aJ)
}aJ.set("children",[]);
if(aJ.get("opened")!=null&&!aJ.get("opened")){aJ.set("opened",false)
}aF=encodeURIComponent(aI);
aL();
z=setInterval(aL,1000);
i=aJ
}function W(aK,aN,aJ){if(q.shared&&aN!=="local"){aE()
}if(i!=null){i.set("opened",true)
}aJ.close=function(){I()
};
if(ay>0&&aK==="re-connecting"){aJ.isReopen=true;
r(ar)
}else{if(ar.error==null){ar.request=aJ;
var aL=ar.state;
ar.state=aK;
var aI=ar.transport;
ar.transport=aN;
var aM=ar.responseBody;
ai();
ar.responseBody=aM;
ar.state=aL;
ar.transport=aI
}}}function aB(aK){aK.transport="jsonp";
var aJ=q,aI;
if((aK!=null)&&(typeof(aK)!=="undefined")){aJ=aK
}Y={open:function(){var aN="atmosphere"+(++S);
function aL(){aJ.lastIndex=0;
if(aJ.openId){clearTimeout(aJ.openId)
}if(aJ.heartbeatTimer){clearTimeout(aJ.heartbeatTimer)
}if(aJ.reconnect&&ay++<aJ.maxReconnectOnClose){W("re-connecting",aJ.transport,aJ);
an(Y,aJ,aK.reconnectInterval);
aJ.openId=setTimeout(function(){Z(aJ)
},aJ.reconnectInterval+1000)
}else{U(0,"maxReconnectOnClose reached")
}}function aM(){var aO=aJ.url;
if(aJ.dispatchUrl!=null){aO+=aJ.dispatchUrl
}var aQ=aJ.data;
if(aJ.attachHeadersAsQueryString){aO=p(aJ);
if(aQ!==""){aO+="&X-Atmosphere-Post-Body="+encodeURIComponent(aQ)
}aQ=""
}var aP=document.head||document.getElementsByTagName("head")[0]||document.documentElement;
aI=document.createElement("script");
aI.src=aO+"&jsonpTransport="+aN;
aI.clean=function(){aI.clean=aI.onerror=aI.onload=aI.onreadystatechange=null;
if(aI.parentNode){aI.parentNode.removeChild(aI)
}if(++aK.scriptCount===2){aK.scriptCount=1;
aL()
}};
aI.onload=aI.onreadystatechange=function(){aH("jsonp.onload");
if(!aI.readyState||/loaded|complete/.test(aI.readyState)){aI.clean()
}};
aI.onerror=function(){aH("jsonp.onerror");
aK.scriptCount=1;
aI.clean()
};
aP.insertBefore(aI,aP.firstChild)
}window[aN]=function(aQ){aH("jsonp.window");
aK.scriptCount=0;
if(aJ.reconnect&&aJ.maxRequest===-1||aJ.requestCount++<aJ.maxRequest){if(!aJ.executeCallbackBeforeReconnect){an(Y,aJ,aJ.pollingInterval)
}if(aQ!=null&&typeof aQ!=="string"){try{aQ=aQ.message
}catch(aP){}}var aO=s(aQ,aJ,ar);
if(!aO){l(ar.responseBody,"messageReceived",200,aJ.transport)
}if(aJ.executeCallbackBeforeReconnect){an(Y,aJ,aJ.pollingInterval)
}k(aJ)
}else{a.util.log(q.logLevel,["JSONP reconnect maximum try reached "+q.requestCount]);
U(0,"maxRequest reached")
}};
setTimeout(function(){aM()
},50)
},abort:function(){if(aI&&aI.clean){aI.clean()
}}};
Y.open()
}function av(aI){if(q.webSocketImpl!=null){return q.webSocketImpl
}else{if(window.WebSocket){return new WebSocket(aI)
}else{return new MozWebSocket(aI)
}}}function w(){return p(q,a.util.getAbsoluteURL(q.webSocketUrl||q.url)).replace(/^http/,"ws")
}function T(){var aI=p(q);
return aI
}function D(aJ){ar.transport="sse";
var aI=T();
if(x("debug")){a.util.debug("Invoking executeSSE");
a.util.debug("Using URL: "+aI)
}if(aJ&&!q.reconnect){if(ag!=null){m()
}return
}try{ag=new EventSource(aI,{withCredentials:q.withCredentials})
}catch(aK){U(0,aK);
az("SSE failed. Downgrading to fallback transport and resending");
return
}if(q.connectTimeout>0){q.id=setTimeout(function(){if(!aJ){m()
}},q.connectTimeout)
}ag.onopen=function(aL){aH("sse.onopen");
k(q);
if(x("debug")){a.util.debug("SSE successfully opened")
}if(!q.enableProtocol){if(!aJ){W("opening","sse",q)
}else{W("re-opening","sse",q)
}}else{if(q.isReopen){q.isReopen=false;
W("re-opening",q.transport,q)
}}aJ=true;
if(q.method==="POST"){ar.state="messageReceived";
ag.send(q.data)
}};
ag.onmessage=function(aM){aH("sse.onmessage");
k(q);
if(!q.enableXDR&&aM.origin&&aM.origin!==window.location.protocol+"//"+window.location.host){a.util.log(q.logLevel,["Origin was not "+window.location.protocol+"//"+window.location.host]);
return
}ar.state="messageReceived";
ar.status=200;
aM=aM.data;
var aL=s(aM,q,ar);
if(!aL){ai();
ar.responseBody="";
ar.messages=[]
}};
ag.onerror=function(aL){aH("sse.onerror");
clearTimeout(q.id);
if(q.heartbeatTimer){clearTimeout(q.heartbeatTimer)
}if(ar.closedByClientTimeout){return
}ae(aJ);
m();
if(aG){a.util.log(q.logLevel,["SSE closed normally"])
}else{if(!aJ){az("SSE failed. Downgrading to fallback transport and resending")
}else{if(q.reconnect&&(ar.transport==="sse")){if(ay++<q.maxReconnectOnClose){W("re-connecting",q.transport,q);
if(q.reconnectInterval>0){q.reconnectId=setTimeout(function(){D(true)
},q.reconnectInterval)
}else{D(true)
}ar.responseBody="";
ar.messages=[]
}else{a.util.log(q.logLevel,["SSE reconnect maximum try reached "+ay]);
U(0,"maxReconnectOnClose reached")
}}}}}
}function af(aJ){ar.transport="websocket";
var aI=w(q.url);
if(x("debug")){a.util.debug("Invoking executeWebSocket");
a.util.debug("Using URL: "+aI)
}if(aJ&&!q.reconnect){if(aw!=null){m()
}return
}aw=av(aI);
if(q.webSocketBinaryType!=null){aw.binaryType=q.webSocketBinaryType
}if(q.connectTimeout>0){q.id=setTimeout(function(){if(!aJ){var aM={code:1002,reason:"",wasClean:false};
aw.onclose(aM);
try{m()
}catch(aN){}return
}},q.connectTimeout)
}aw.onopen=function(aN){aH("websocket.onopen");
k(q);
d=false;
if(x("debug")){a.util.debug("Websocket successfully opened")
}var aM=aJ;
if(aw!=null){aw.canSendMessage=true
}if(!q.enableProtocol){aJ=true;
if(aM){W("re-opening","websocket",q)
}else{W("opening","websocket",q)
}}if(aw!=null){if(q.method==="POST"){ar.state="messageReceived";
aw.send(q.data)
}}};
aw.onmessage=function(aO){aH("websocket.onmessage");
k(q);
if(q.enableProtocol){aJ=true
}ar.state="messageReceived";
ar.status=200;
aO=aO.data;
var aM=typeof(aO)==="string";
if(aM){var aN=s(aO,q,ar);
if(!aN){ai();
ar.responseBody="";
ar.messages=[]
}}else{aO=t(q,aO);
if(aO===""){return
}ar.responseBody=aO;
ai();
ar.responseBody=null
}};
aw.onerror=function(aM){aH("websocket.onerror");
clearTimeout(q.id);
if(q.heartbeatTimer){clearTimeout(q.heartbeatTimer)
}};
aw.onclose=function(aM){aH("websocket.onclose");
clearTimeout(q.id);
if(ar.state==="closed"){return
}var aN=aM.reason;
if(aN===""){switch(aM.code){case 1000:aN="Normal closure; the connection successfully completed whatever purpose for which it was created.";
break;
case 1001:aN="The endpoint is going away, either because of a server failure or because the browser is navigating away from the page that opened the connection.";
break;
case 1002:aN="The endpoint is terminating the connection due to a protocol error.";
break;
case 1003:aN="The connection is being terminated because the endpoint received data of a type it cannot accept (for example, a text-only endpoint received binary data).";
break;
case 1004:aN="The endpoint is terminating the connection because a data frame was received that is too large.";
break;
case 1005:aN="Unknown: no status code was provided even though one was expected.";
break;
case 1006:aN="Connection was closed abnormally (that is, with no close frame being sent).";
break
}}if(x("warn")){a.util.warn("Websocket closed, reason: "+aN);
a.util.warn("Websocket closed, wasClean: "+aM.wasClean)
}if(ar.closedByClientTimeout||d){if(q.reconnectId){clearTimeout(q.reconnectId);
delete q.reconnectId
}return
}ae(aJ);
ar.state="closed";
if(aG){a.util.log(q.logLevel,["Websocket closed normally"])
}else{if(!aJ){az("Websocket failed. Downgrading to Comet and resending")
}else{if(q.reconnect&&ar.transport==="websocket"){m();
if(ay++<q.maxReconnectOnClose){W("re-connecting",q.transport,q);
if(q.reconnectInterval>0){q.reconnectId=setTimeout(function(){ar.responseBody="";
ar.messages=[];
af(true)
},q.reconnectInterval)
}else{ar.responseBody="";
ar.messages=[];
af(true)
}}else{a.util.log(q.logLevel,["Websocket reconnect maximum try reached "+q.requestCount]);
if(x("warn")){a.util.warn("Websocket error, reason: "+aM.reason)
}U(0,"maxReconnectOnClose reached")
}}}}};
var aK=navigator.userAgent.toLowerCase();
var aL=aK.indexOf("android")>-1;
if(aL&&aw.url===undefined){aw.onclose({reason:"Android 4.1 does not support websockets.",wasClean:false})
}}function t(aM,aL){var aK=aL;
if(aM.transport==="polling"){return aK
}if(aM.enableProtocol&&aM.firstMessage&&a.util.trim(aL).length!==0){var aN=aM.trackMessageLength?1:0;
var aJ=aL.split(aM.messageDelimiter);
if(aJ.length<=aN+1){return aK
}aM.firstMessage=false;
aM.uuid=a.util.trim(aJ[aN]);
if(aJ.length<=aN+2){a.util.log("error",["Protocol data not sent by the server. If you enable protocol on client side, be sure to install JavascriptProtocol interceptor on server side.Also note that atmosphere-runtime 2.2+ should be used."])
}K=parseInt(a.util.trim(aJ[aN+1]),10);
ak=aJ[aN+2];
if(aM.transport!=="long-polling"){Z(aM)
}f=aM.uuid;
aK="";
aN=aM.trackMessageLength?4:3;
if(aJ.length>aN+1){for(var aI=aN;
aI<aJ.length;
aI++){aK+=aJ[aI];
if(aI+1!==aJ.length){aK+=aM.messageDelimiter
}}}if(aM.ackInterval!==0){setTimeout(function(){u("...ACK...")
},aM.ackInterval)
}}else{if(aM.enableProtocol&&aM.firstMessage&&a.util.browser.msie&&+a.util.browser.version.split(".")[0]<10){a.util.log(q.logLevel,["Receiving unexpected data from IE"])
}else{Z(aM)
}}return aK
}function k(aI){clearTimeout(aI.id);
if(aI.timeout>0&&aI.transport!=="polling"){aI.id=setTimeout(function(){aC(aI);
E();
m()
},aI.timeout)
}}function aC(aI){ar.closedByClientTimeout=true;
ar.state="closedByClient";
ar.responseBody="";
ar.status=408;
ar.messages=[];
ai()
}function U(aI,aJ){m();
clearTimeout(q.id);
ar.state="error";
ar.reasonPhrase=aJ;
ar.responseBody="";
ar.status=aI;
ar.messages=[];
ai()
}function s(aM,aL,aI){aM=t(aL,aM);
if(aM.length===0){return true
}aI.responseBody=aM;
if(aL.trackMessageLength){aM=aI.partialMessage+aM;
var aK=[];
var aJ=aM.indexOf(aL.messageDelimiter);
if(aJ!=-1){while(aJ!==-1){var aO=aM.substring(0,aJ);
var aN=+aO;
if(isNaN(aN)){throw new Error('message length "'+aO+'" is not a number')
}aJ+=aL.messageDelimiter.length;
if(aJ+aN>aM.length){aJ=-1
}else{aK.push(aM.substring(aJ,aJ+aN));
aM=aM.substring(aJ+aN,aM.length);
aJ=aM.indexOf(aL.messageDelimiter)
}}aI.partialMessage=aM;
if(aK.length!==0){aI.responseBody=aK.join(aL.messageDelimiter);
aI.messages=aK;
return false
}else{aI.responseBody="";
aI.messages=[];
return true
}}}aI.responseBody=aM;
aI.messages=[aM];
return false
}function az(aI){a.util.log(q.logLevel,[aI]);
if(typeof(q.onTransportFailure)!=="undefined"){q.onTransportFailure(aI,q)
}else{if(typeof(a.util.onTransportFailure)!=="undefined"){a.util.onTransportFailure(aI,q)
}}q.transport=q.fallbackTransport;
var aJ=q.connectTimeout===-1?0:q.connectTimeout;
if(q.reconnect&&q.transport!=="none"||q.transport==null){q.method=q.fallbackMethod;
ar.transport=q.fallbackTransport;
q.fallbackTransport="none";
if(aJ>0){q.reconnectId=setTimeout(function(){aa()
},aJ)
}else{aa()
}}else{U(500,"Unable to reconnect with fallback transport")
}}function p(aK,aI){var aJ=q;
if((aK!=null)&&(typeof(aK)!=="undefined")){aJ=aK
}if(aI==null){aI=aJ.url
}if(!aJ.attachHeadersAsQueryString){return aI
}if(aI.indexOf("X-Atmosphere-Framework")!==-1){return aI
}aI+=(aI.indexOf("?")!==-1)?"&":"?";
aI+="X-Atmosphere-tracking-id="+aJ.uuid;
aI+="&X-Atmosphere-Framework="+c;
aI+="&X-Atmosphere-Transport="+aJ.transport;
if(aJ.trackMessageLength){aI+="&X-Atmosphere-TrackMessageSize=true"
}if(aJ.heartbeat!==null&&aJ.heartbeat.server!==null){aI+="&X-Heartbeat-Server="+aJ.heartbeat.server
}if(aJ.contentType!==""){aI+="&Content-Type="+(aJ.transport==="websocket"?aJ.contentType:encodeURIComponent(aJ.contentType))
}if(aJ.enableProtocol){aI+="&X-atmo-protocol=true"
}a.util.each(aJ.headers,function(aL,aN){var aM=a.util.isFunction(aN)?aN.call(this,aJ,aK,ar):aN;
if(aM!=null){aI+="&"+encodeURIComponent(aL)+"="+encodeURIComponent(aM)
}});
return aI
}function Z(aI){if(!aI.isOpen){aI.isOpen=true;
W("opening",aI.transport,aI)
}else{if(aI.isReopen){aI.isReopen=false;
W("re-opening",aI.transport,aI)
}else{if(ar.state==="messageReceived"&&(aI.transport==="jsonp"||aI.transport==="long-polling")){ap(ar)
}else{return
}}}C(aI)
}function C(aJ){if(aJ.heartbeatTimer!=null){clearTimeout(aJ.heartbeatTimer)
}if(!isNaN(K)&&K>0){var aI=function(){if(x("debug")){a.util.debug("Sending heartbeat")
}u(ak);
aJ.heartbeatTimer=setTimeout(aI,K)
};
aJ.heartbeatTimer=setTimeout(aI,K)
}}function M(aL){var aJ=q;
if((aL!=null)||(typeof(aL)!=="undefined")){aJ=aL
}aJ.lastIndex=0;
aJ.readyState=0;
if((aJ.transport==="jsonp")||((aJ.enableXDR)&&(a.util.checkCORSSupport()))){aB(aJ);
return
}if(a.util.browser.msie&&+a.util.browser.version.split(".")[0]<10){if((aJ.transport==="streaming")){if(aJ.enableXDR&&window.XDomainRequest){Q(aJ)
}else{aA(aJ)
}return
}if((aJ.enableXDR)&&(window.XDomainRequest)){Q(aJ);
return
}}var aM=function(aO){aJ.lastIndex=0;
if(aO||(aJ.reconnect&&ay++<aJ.maxReconnectOnClose)){ar.ffTryingReconnect=true;
W("re-connecting",aL.transport,aL);
an(aK,aJ,aL.reconnectInterval)
}else{U(0,"maxReconnectOnClose reached")
}};
var aI=function(){ar.errorHandled=true;
m();
aM(false)
};
if(aJ.force||(aJ.reconnect&&(aJ.maxRequest===-1||aJ.requestCount++<aJ.maxRequest))){aJ.force=false;
var aK=a.util.xhr();
aK.hasData=false;
N(aK,aJ,true);
if(aJ.suspend){A=aK
}if(aJ.transport!=="polling"){ar.transport=aJ.transport;
aK.onabort=function(){aH("ajaxrequest.onabort");
ae(true)
};
aK.onerror=function(){aH("ajaxrequest.onerror");
ar.error=true;
ar.ffTryingReconnect=true;
try{ar.status=XMLHttpRequest.status
}catch(aO){ar.status=500
}if(!ar.status){ar.status=500
}if(!ar.errorHandled){m();
aM(false)
}}
}aK.onreadystatechange=function(){aH("ajaxRequest.onreadystatechange, new state: "+aK.readyState);
if(aG){return
}ar.error=null;
var aP=false;
var aV=false;
if(aJ.transport==="streaming"&&aJ.readyState>2&&aK.readyState===4){m();
aM(false);
return
}aJ.readyState=aK.readyState;
if(aJ.transport==="streaming"&&aK.readyState>=3){aV=true
}else{if(aJ.transport==="long-polling"&&aK.readyState===4){aV=true
}}k(q);
if(aJ.transport!=="polling"){var aO=200;
if(aK.readyState===4){aO=aK.status>1000?0:aK.status
}if(!aJ.reconnectOnServerError&&(aO>=300&&aO<600)){U(aO,aK.statusText);
return
}if(aO>=300||aO===0){aI();
return
}if((!aJ.enableProtocol||!aL.firstMessage)&&aK.readyState===2){if(a.util.browser.mozilla&&ar.ffTryingReconnect){ar.ffTryingReconnect=false;
setTimeout(function(){if(!ar.ffTryingReconnect){Z(aJ)
}},500)
}else{Z(aJ)
}}}else{if(aK.readyState===4){aV=true
}}if(aV){var aS=aK.responseText;
ar.errorHandled=false;
if(aJ.transport==="long-polling"&&a.util.trim(aS).length===0){if(!aK.hasData){aM(true)
}else{aK.hasData=false
}return
}aK.hasData=true;
H(aK,q);
if(aJ.transport==="streaming"){if(!a.util.browser.opera){var aR=aS.substring(aJ.lastIndex,aS.length);
aP=s(aR,aJ,ar);
aJ.lastIndex=aS.length;
if(aP){return
}}else{a.util.iterate(function(){if(ar.status!==500&&aK.responseText.length>aJ.lastIndex){try{ar.status=aK.status;
ar.headers=a.util.parseHeaders(aK.getAllResponseHeaders());
H(aK,q)
}catch(aX){ar.status=404
}k(q);
ar.state="messageReceived";
var aW=aK.responseText.substring(aJ.lastIndex);
aJ.lastIndex=aK.responseText.length;
aP=s(aW,aJ,ar);
if(!aP){ai()
}if(J(aK,aJ)){L(aK,aJ);
return
}}else{if(ar.status>400){aJ.lastIndex=aK.responseText.length;
return false
}}},0)
}}else{aP=s(aS,aJ,ar)
}var aU=J(aK,aJ);
try{ar.status=aK.status;
ar.headers=a.util.parseHeaders(aK.getAllResponseHeaders());
H(aK,aJ)
}catch(aT){ar.status=404
}if(aJ.suspend){ar.state=ar.status===0?"closed":"messageReceived"
}else{ar.state="messagePublished"
}var aQ=!aU&&aL.transport!=="streaming"&&aL.transport!=="polling";
if(aQ&&!aJ.executeCallbackBeforeReconnect){an(aK,aJ,aJ.pollingInterval)
}if(ar.responseBody.length!==0&&!aP){ai()
}if(aQ&&aJ.executeCallbackBeforeReconnect){an(aK,aJ,aJ.pollingInterval)
}if(aU){L(aK,aJ)
}}};
try{aK.send(aJ.data);
v=true
}catch(aN){a.util.log(aJ.logLevel,["Unable to connect to "+aJ.url]);
U(0,aN)
}}else{if(aJ.logLevel==="debug"){a.util.log(aJ.logLevel,["Max re-connection reached."])
}U(0,"maxRequest reached")
}}function L(aJ,aI){ar.messages=[];
aI.isReopen=true;
I();
aG=false;
an(aJ,aI,500)
}function N(aK,aL,aJ){var aI=aL.url;
if(aL.dispatchUrl!=null&&aL.method==="POST"){aI+=aL.dispatchUrl
}aI=p(aL,aI);
aI=a.util.prepareURL(aI);
if(aJ){aK.open(aL.method,aI,aL.async);
if(aL.connectTimeout>0){aL.id=setTimeout(function(){if(aL.requestCount===0){m();
l("Connect timeout","closed",200,aL.transport)
}},aL.connectTimeout)
}}if(q.withCredentials&&q.transport!=="websocket"){if("withCredentials" in aK){aK.withCredentials=true
}}if(!q.dropHeaders){aK.setRequestHeader("X-Atmosphere-Framework",c);
aK.setRequestHeader("X-Atmosphere-Transport",aL.transport);
if(aL.heartbeat!==null&&aL.heartbeat.server!==null){aK.setRequestHeader("X-Heartbeat-Server",aK.heartbeat.server)
}if(aL.trackMessageLength){aK.setRequestHeader("X-Atmosphere-TrackMessageSize","true")
}aK.setRequestHeader("X-Atmosphere-tracking-id",aL.uuid);
a.util.each(aL.headers,function(aM,aO){var aN=a.util.isFunction(aO)?aO.call(this,aK,aL,aJ,ar):aO;
if(aN!=null){aK.setRequestHeader(aM,aN)
}})
}if(aL.contentType!==""){aK.setRequestHeader("Content-Type",aL.contentType)
}}function an(aJ,aK,aL){if(ar.closedByClientTimeout){return
}if(aK.reconnect||(aK.suspend&&v)){var aI=0;
if(aJ&&aJ.readyState>1){aI=aJ.status>1000?0:aJ.status
}ar.status=aI===0?204:aI;
ar.reason=aI===0?"Server resumed the connection or down.":"OK";
clearTimeout(aK.id);
if(aK.reconnectId){clearTimeout(aK.reconnectId);
delete aK.reconnectId
}if(aL>0){q.reconnectId=setTimeout(function(){M(aK)
},aL)
}else{M(aK)
}}}function r(aI){aI.state="re-connecting";
al(aI)
}function ap(aI){aI.state="openAfterResume";
al(aI);
aI.state="messageReceived"
}function Q(aI){if(aI.transport!=="polling"){o=ac(aI);
o.open()
}else{ac(aI).open()
}}function ac(aK){var aJ=q;
if((aK!=null)&&(typeof(aK)!=="undefined")){aJ=aK
}var aP=aJ.transport;
var aO=0;
var aI=new window.XDomainRequest();
var aM=function(){if(aJ.transport==="long-polling"&&(aJ.reconnect&&(aJ.maxRequest===-1||aJ.requestCount++<aJ.maxRequest))){aI.status=200;
Q(aJ)
}};
var aN=aJ.rewriteURL||function(aR){var aQ=/(?:^|;\s*)(JSESSIONID|PHPSESSID)=([^;]*)/.exec(document.cookie);
switch(aQ&&aQ[1]){case"JSESSIONID":return aR.replace(/;jsessionid=[^\?]*|(\?)|$/,";jsessionid="+aQ[2]+"$1");
case"PHPSESSID":return aR.replace(/\?PHPSESSID=[^&]*&?|\?|$/,"?PHPSESSID="+aQ[2]+"&").replace(/&$/,"")
}return aR
};
aI.onprogress=function(){aL(aI)
};
aI.onerror=function(){if(aJ.transport!=="polling"){m();
if(ay++<aJ.maxReconnectOnClose){if(aJ.reconnectInterval>0){aJ.reconnectId=setTimeout(function(){W("re-connecting",aK.transport,aK);
Q(aJ)
},aJ.reconnectInterval)
}else{W("re-connecting",aK.transport,aK);
Q(aJ)
}}else{U(0,"maxReconnectOnClose reached")
}}};
aI.onload=function(){};
var aL=function(aQ){clearTimeout(aJ.id);
var aS=aQ.responseText;
aS=aS.substring(aO);
aO+=aS.length;
if(aP!=="polling"){k(aJ);
var aR=s(aS,aJ,ar);
if(aP==="long-polling"&&a.util.trim(aS).length===0){return
}if(aJ.executeCallbackBeforeReconnect){aM()
}if(!aR){l(ar.responseBody,"messageReceived",200,aP)
}if(!aJ.executeCallbackBeforeReconnect){aM()
}}};
return{open:function(){var aQ=aJ.url;
if(aJ.dispatchUrl!=null){aQ+=aJ.dispatchUrl
}aQ=p(aJ,aQ);
aI.open(aJ.method,aN(aQ));
if(aJ.method==="GET"){aI.send()
}else{aI.send(aJ.data)
}if(aJ.connectTimeout>0){aJ.id=setTimeout(function(){if(aJ.requestCount===0){m();
l("Connect timeout","closed",200,aJ.transport)
}},aJ.connectTimeout)
}},close:function(){aI.abort()
}}
}function aA(aI){o=ab(aI);
o.open()
}function ab(aL){var aK=q;
if((aL!=null)&&(typeof(aL)!=="undefined")){aK=aL
}var aJ;
var aM=new window.ActiveXObject("htmlfile");
aM.open();
aM.close();
var aI=aK.url;
if(aK.dispatchUrl!=null){aI+=aK.dispatchUrl
}if(aK.transport!=="polling"){ar.transport=aK.transport
}return{open:function(){var aN=aM.createElement("iframe");
aI=p(aK);
if(aK.data!==""){aI+="&X-Atmosphere-Post-Body="+encodeURIComponent(aK.data)
}aI=a.util.prepareURL(aI);
aN.src=aI;
aM.body.appendChild(aN);
var aO=aN.contentDocument||aN.contentWindow.document;
aJ=a.util.iterate(function(){try{if(!aO.firstChild){return
}var aR=aO.body?aO.body.lastChild:aO;
var aT=function(){var aV=aR.cloneNode(true);
aV.appendChild(aO.createTextNode("."));
var aU=aV.innerText;
aU=aU.substring(0,aU.length-1);
return aU
};
if(!aO.body||!aO.body.firstChild||aO.body.firstChild.nodeName.toLowerCase()!=="pre"){var aQ=aO.head||aO.getElementsByTagName("head")[0]||aO.documentElement||aO;
var aP=aO.createElement("script");
aP.text="document.write('<plaintext>')";
aQ.insertBefore(aP,aQ.firstChild);
aQ.removeChild(aP);
aR=aO.body.lastChild
}if(aK.closed){aK.isReopen=true
}aJ=a.util.iterate(function(){var aV=aT();
if(aV.length>aK.lastIndex){k(q);
ar.status=200;
ar.error=null;
aR.innerText="";
var aU=s(aV,aK,ar);
if(aU){return""
}l(ar.responseBody,"messageReceived",200,aK.transport)
}aK.lastIndex=0;
if(aO.readyState==="complete"){ae(true);
W("re-connecting",aK.transport,aK);
if(aK.reconnectInterval>0){aK.reconnectId=setTimeout(function(){aA(aK)
},aK.reconnectInterval)
}else{aA(aK)
}return false
}},null);
return false
}catch(aS){ar.error=true;
W("re-connecting",aK.transport,aK);
if(ay++<aK.maxReconnectOnClose){if(aK.reconnectInterval>0){aK.reconnectId=setTimeout(function(){aA(aK)
},aK.reconnectInterval)
}else{aA(aK)
}}else{U(0,"maxReconnectOnClose reached")
}aM.execCommand("Stop");
aM.close();
return false
}})
},close:function(){if(aJ){aJ()
}aM.execCommand("Stop");
ae(true)
}}
}function u(aI){if(ax!=null){F(aI)
}else{if(A!=null||ag!=null){P(aI)
}else{if(o!=null){j(aI)
}else{if(Y!=null){B(aI)
}else{if(aw!=null){X(aI)
}else{U(0,"No suspended connection available");
a.util.error("No suspended connection available. Make sure atmosphere.subscribe has been called and request.onOpen invoked before trying to push data")
}}}}}}function am(aJ,aI){if(!aI){aI=y(aJ)
}aI.transport="polling";
aI.method="GET";
aI.withCredentials=false;
aI.reconnect=false;
aI.force=true;
aI.suspend=false;
aI.timeout=1000;
M(aI)
}function F(aI){ax.send(aI)
}function aq(aJ){if(aJ.length===0){return
}try{if(ax){ax.localSend(aJ)
}else{if(i){i.signal("localMessage",a.util.stringifyJSON({id:S,event:aJ}))
}}}catch(aI){a.util.error(aI)
}}function P(aJ){var aI=y(aJ);
M(aI)
}function j(aJ){if(q.enableXDR&&a.util.checkCORSSupport()){var aI=y(aJ);
aI.reconnect=false;
aB(aI)
}else{P(aJ)
}}function B(aI){P(aI)
}function O(aI){var aJ=aI;
if(typeof(aJ)==="object"){aJ=aI.data
}return aJ
}function y(aJ){var aK=O(aJ);
var aI={connected:false,timeout:60000,method:"POST",url:q.url,contentType:q.contentType,headers:q.headers,reconnect:true,callback:null,data:aK,suspend:false,maxRequest:-1,logLevel:"info",requestCount:0,withCredentials:q.withCredentials,async:q.async,transport:"polling",isOpen:true,attachHeadersAsQueryString:true,enableXDR:q.enableXDR,uuid:q.uuid,dispatchUrl:q.dispatchUrl,enableProtocol:false,messageDelimiter:"|",trackMessageLength:q.trackMessageLength,maxReconnectOnClose:q.maxReconnectOnClose,heartbeatTimer:q.heartbeatTimer,heartbeat:q.heartbeat};
if(typeof(aJ)==="object"){aI=a.util.extend(aI,aJ)
}return aI
}function X(aI){var aL=a.util.isBinary(aI)?aI:O(aI);
var aJ;
try{if(q.dispatchUrl!=null){aJ=q.webSocketPathDelimiter+q.dispatchUrl+q.webSocketPathDelimiter+aL
}else{aJ=aL
}if(!aw.canSendMessage){a.util.error("WebSocket not connected.");
return
}aw.send(aJ)
}catch(aK){aw.onclose=function(aM){};
m();
az("Websocket failed. Downgrading to Comet and resending "+aI);
P(aI)
}}function G(aJ){var aI=a.util.parseJSON(aJ);
if(aI.id!==S){if(typeof(q.onLocalMessage)!=="undefined"){q.onLocalMessage(aI.event)
}else{if(typeof(a.util.onLocalMessage)!=="undefined"){a.util.onLocalMessage(aI.event)
}}}}function l(aL,aI,aJ,aK){ar.responseBody=aL;
ar.transport=aK;
ar.status=aJ;
ar.state=aI;
ai()
}function H(aI,aK){if(!aK.readResponsesHeaders){if(!aK.enableProtocol){aK.uuid=S
}}else{try{var aJ=aI.getResponseHeader("X-Atmosphere-tracking-id");
if(aJ&&aJ!=null){aK.uuid=aJ.split(" ").pop()
}}catch(aL){}}}function al(aI){n(aI,q);
n(aI,a.util)
}function n(aJ,aK){switch(aJ.state){case"messageReceived":aH("Firing onMessage");
ay=0;
if(typeof(aK.onMessage)!=="undefined"){aK.onMessage(aJ)
}if(typeof(aK.onmessage)!=="undefined"){aK.onmessage(aJ)
}break;
case"error":aH("Firing onError");
if(typeof(aK.onError)!=="undefined"){aK.onError(aJ)
}if(typeof(aK.onerror)!=="undefined"){aK.onerror(aJ)
}break;
case"opening":delete q.closed;
aH("Firing onOpen");
if(typeof(aK.onOpen)!=="undefined"){aK.onOpen(aJ)
}if(typeof(aK.onopen)!=="undefined"){aK.onopen(aJ)
}break;
case"messagePublished":aH("Firing messagePublished");
if(typeof(aK.onMessagePublished)!=="undefined"){aK.onMessagePublished(aJ)
}break;
case"re-connecting":aH("Firing onReconnect");
if(typeof(aK.onReconnect)!=="undefined"){aK.onReconnect(q,aJ)
}break;
case"closedByClient":aH("Firing closedByClient");
if(typeof(aK.onClientTimeout)!=="undefined"){aK.onClientTimeout(q)
}break;
case"re-opening":delete q.closed;
aH("Firing onReopen");
if(typeof(aK.onReopen)!=="undefined"){aK.onReopen(q,aJ)
}break;
case"fail-to-reconnect":aH("Firing onFailureToReconnect");
if(typeof(aK.onFailureToReconnect)!=="undefined"){aK.onFailureToReconnect(q,aJ)
}break;
case"unsubscribe":case"closed":var aI=typeof(q.closed)!=="undefined"?q.closed:false;
if(!aI){aH("Firing onClose");
if(typeof(aK.onClose)!=="undefined"){aK.onClose(aJ)
}if(typeof(aK.onclose)!=="undefined"){aK.onclose(aJ)
}}else{aH("Closed but not firing onClose")
}q.closed=true;
break;
case"openAfterResume":if(typeof(aK.onOpenAfterResume)!=="undefined"){aK.onOpenAfterResume(q)
}break
}}function ae(aI){if(ar.state!=="closed"){ar.state="closed";
ar.responseBody="";
ar.messages=[];
ar.status=!aI?501:200;
ai()
}}function ai(){var aK=function(aN,aO){aO(ar)
};
if(ax==null&&R!=null){R(ar.responseBody)
}q.reconnect=q.mrequest;
var aI=typeof(ar.responseBody)==="string";
var aL=(aI&&q.trackMessageLength)?(ar.messages.length>0?ar.messages:[""]):new Array(ar.responseBody);
for(var aJ=0;
aJ<aL.length;
aJ++){if(aL.length>1&&aL[aJ].length===0){continue
}ar.responseBody=(aI)?a.util.trim(aL[aJ]):aL[aJ];
if(ax==null&&R!=null){R(ar.responseBody)
}if((ar.responseBody.length===0||(aI&&ak===ar.responseBody))&&ar.state==="messageReceived"){continue
}al(ar);
if(g.length>0){if(x("debug")){a.util.debug("Invoking "+g.length+" global callbacks: "+ar.state)
}try{a.util.each(g,aK)
}catch(aM){a.util.log(q.logLevel,["Callback exception"+aM])
}}if(typeof(q.callback)==="function"){if(x("debug")){a.util.debug("Invoking request callbacks")
}try{q.callback(ar)
}catch(aM){a.util.log(q.logLevel,["Callback exception"+aM])
}}}}this.subscribe=function(aI){ao(aI);
aa()
};
this.execute=function(){aa()
};
this.close=function(){I()
};
this.disconnect=function(){E()
};
this.getUrl=function(){return q.url
};
this.push=function(aK,aJ){if(aJ!=null){var aI=q.dispatchUrl;
q.dispatchUrl=aJ;
u(aK);
q.dispatchUrl=aI
}else{u(aK)
}};
this.getUUID=function(){return q.uuid
};
this.pushLocal=function(aI){aq(aI)
};
this.enableProtocol=function(aI){return q.enableProtocol
};
this.init=function(){aj()
};
this.request=q;
this.response=ar
}};
a.subscribe=function(i,l,k){if(typeof(l)==="function"){a.addCallback(l)
}if(typeof(i)!=="string"){k=i
}else{k.url=i
}f=((typeof(k)!=="undefined")&&typeof(k.uuid)!=="undefined")?k.uuid:0;
var j=new a.AtmosphereRequest(k);
j.execute();
h[h.length]=j;
return j
};
a.unsubscribe=function(){if(h.length>0){var j=[].concat(h);
for(var l=0;
l<j.length;
l++){var k=j[l];
k.close();
clearTimeout(k.response.request.id);
if(k.heartbeatTimer){clearTimeout(k.heartbeatTimer)
}}}h=[];
g=[]
};
a.unsubscribeUrl=function(k){var j=-1;
if(h.length>0){for(var m=0;
m<h.length;
m++){var l=h[m];
if(l.getUrl()===k){l.close();
clearTimeout(l.response.request.id);
if(l.heartbeatTimer){clearTimeout(l.heartbeatTimer)
}j=m;
break
}}}if(j>=0){h.splice(j,1)
}};
a.addCallback=function(i){if(a.util.inArray(i,g)===-1){g.push(i)
}};
a.removeCallback=function(j){var i=a.util.inArray(j,g);
if(i!==-1){g.splice(i,1)
}};
a.util={browser:{},parseHeaders:function(j){var i,l=/^(.*?):[ \t]*([^\r\n]*)\r?$/mg,k={};
while(i=l.exec(j)){k[i[1]]=i[2]
}return k
},now:function(){return new Date().getTime()
},isArray:function(i){return Object.prototype.toString.call(i)==="[object Array]"
},inArray:function(l,m){if(!Array.prototype.indexOf){var j=m.length;
for(var k=0;
k<j;
++k){if(m[k]===l){return k
}}return -1
}return m.indexOf(l)
},isBinary:function(i){return/^\[object\s(?:Blob|ArrayBuffer|.+Array)\]$/.test(Object.prototype.toString.call(i))
},isFunction:function(i){return Object.prototype.toString.call(i)==="[object Function]"
},getAbsoluteURL:function(i){var j=document.createElement("div");
j.innerHTML='<a href="'+i+'"/>';
return encodeURI(decodeURI(j.firstChild.href))
},prepareURL:function(j){var k=a.util.now();
var i=j.replace(/([?&])_=[^&]*/,"$1_="+k);
return i+(i===j?(/\?/.test(j)?"&":"?")+"_="+k:"")
},trim:function(i){if(!String.prototype.trim){return i.toString().replace(/(?:(?:^|\n)\s+|\s+(?:$|\n))/g,"").replace(/\s+/g," ")
}else{return i.toString().trim()
}},param:function(m){var k,i=[];
function l(n,o){o=a.util.isFunction(o)?o():(o==null?"":o);
i.push(encodeURIComponent(n)+"="+encodeURIComponent(o))
}function j(o,p){var n;
if(a.util.isArray(p)){a.util.each(p,function(r,q){if(/\[\]$/.test(o)){l(o,q)
}else{j(o+"["+(typeof q==="object"?r:"")+"]",q)
}})
}else{if(Object.prototype.toString.call(p)==="[object Object]"){for(n in p){j(o+"["+n+"]",p[n])
}}else{l(o,p)
}}}for(k in m){j(k,m[k])
}return i.join("&").replace(/%20/g,"+")
},storage:function(){try{return !!(window.localStorage&&window.StorageEvent)
}catch(i){return false
}},iterate:function(k,j){var l;
j=j||0;
(function i(){l=setTimeout(function(){if(k()===false){return
}i()
},j)
})();
return function(){clearTimeout(l)
}
},each:function(o,p,k){if(!o){return
}var n,l=0,m=o.length,j=a.util.isArray(o);
if(k){if(j){for(;
l<m;
l++){n=p.apply(o[l],k);
if(n===false){break
}}}else{for(l in o){n=p.apply(o[l],k);
if(n===false){break
}}}}else{if(j){for(;
l<m;
l++){n=p.call(o[l],l,o[l]);
if(n===false){break
}}}else{for(l in o){n=p.call(o[l],l,o[l]);
if(n===false){break
}}}}return o
},extend:function(m){var l,k,j;
for(l=1;
l<arguments.length;
l++){if((k=arguments[l])!=null){for(j in k){m[j]=k[j]
}}}return m
},on:function(k,j,i){if(k.addEventListener){k.addEventListener(j,i,false)
}else{if(k.attachEvent){k.attachEvent("on"+j,i)
}}},off:function(k,j,i){if(k.removeEventListener){k.removeEventListener(j,i,false)
}else{if(k.detachEvent){k.detachEvent("on"+j,i)
}}},log:function(k,j){if(window.console){var i=window.console[k];
if(typeof i==="function"){i.apply(window.console,j)
}}},warn:function(){a.util.log("warn",arguments)
},info:function(){a.util.log("info",arguments)
},debug:function(){a.util.log("debug",arguments)
},error:function(){a.util.log("error",arguments)
},xhr:function(){try{return new window.XMLHttpRequest()
}catch(j){try{return new window.ActiveXObject("Microsoft.XMLHTTP")
}catch(i){}}},parseJSON:function(i){return !i?null:window.JSON&&window.JSON.parse?window.JSON.parse(i):new Function("return "+i)()
},stringifyJSON:function(k){var n=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,l={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"};
function i(o){return'"'+o.replace(n,function(p){var q=l[p];
return typeof q==="string"?q:"\\u"+("0000"+p.charCodeAt(0).toString(16)).slice(-4)
})+'"'
}function j(o){return o<10?"0"+o:o
}return window.JSON&&window.JSON.stringify?window.JSON.stringify(k):(function m(t,s){var r,q,o,p,w=s[t],u=typeof w;
if(w&&typeof w==="object"&&typeof w.toJSON==="function"){w=w.toJSON(t);
u=typeof w
}switch(u){case"string":return i(w);
case"number":return isFinite(w)?String(w):"null";
case"boolean":return String(w);
case"object":if(!w){return"null"
}switch(Object.prototype.toString.call(w)){case"[object Date]":return isFinite(w.valueOf())?'"'+w.getUTCFullYear()+"-"+j(w.getUTCMonth()+1)+"-"+j(w.getUTCDate())+"T"+j(w.getUTCHours())+":"+j(w.getUTCMinutes())+":"+j(w.getUTCSeconds())+'Z"':"null";
case"[object Array]":o=w.length;
p=[];
for(r=0;
r<o;
r++){p.push(m(r,w)||"null")
}return"["+p.join(",")+"]";
default:p=[];
for(r in w){if(b.call(w,r)){q=m(r,w);
if(q){p.push(i(r)+":"+q)
}}}return"{"+p.join(",")+"}"
}}})("",{"":k})
},checkCORSSupport:function(){if(a.util.browser.msie&&!window.XDomainRequest&&+a.util.browser.version.split(".")[0]<11){return true
}else{if(a.util.browser.opera&&+a.util.browser.version.split(".")<12){return true
}else{if(a.util.trim(navigator.userAgent).slice(0,16)==="KreaTVWebKit/531"){return true
}else{if(a.util.trim(navigator.userAgent).slice(-7).toLowerCase()==="kreatel"){return true
}}}}var i=navigator.userAgent.toLowerCase();
var j=i.indexOf("android")>-1;
if(j){return true
}return false
}};
e=a.util.now();
(function(){var j=navigator.userAgent.toLowerCase(),i=/(chrome)[ \/]([\w.]+)/.exec(j)||/(opera)(?:.*version|)[ \/]([\w.]+)/.exec(j)||/(msie) ([\w.]+)/.exec(j)||/(trident)(?:.*? rv:([\w.]+)|)/.exec(j)||j.indexOf("android")<0&&/version\/(.+) (safari)/.exec(j)||j.indexOf("compatible")<0&&/(mozilla)(?:.*? rv:([\w.]+)|)/.exec(j)||[];
if(i[2]==="safari"){i[2]=i[1];
i[1]="safari"
}a.util.browser[i[1]||""]=true;
a.util.browser.version=i[2]||"0";
a.util.browser.vmajor=a.util.browser.version.split(".")[0];
if(a.util.browser.trident){a.util.browser.msie=true
}if(a.util.browser.msie||(a.util.browser.mozilla&&+a.util.browser.version.split(".")[0]===1)){a.util.storage=false
}})();
a.util.on(window,"unload",function(i){a.util.debug(new Date()+" Atmosphere: unload event");
a.unsubscribe()
});
a.util.on(window,"beforeunload",function(i){a.util.debug(new Date()+" Atmosphere: beforeunload event")
});
a.util.on(window,"keypress",function(i){if(i.charCode===27||i.keyCode===27){if(i.preventDefault){i.preventDefault()
}}});
a.util.on(window,"offline",function(){d=true;
if(h.length>0){var j=[].concat(h);
for(var l=0;
l<j.length;
l++){var k=j[l];
k.close();
clearTimeout(k.response.request.id);
if(k.heartbeatTimer){clearTimeout(k.heartbeatTimer)
}}}});
a.util.on(window,"online",function(){if(h.length>0){for(var j=0;
j<h.length;
j++){h[j].init();
h[j].execute()
}}d=false
});
return a
}));