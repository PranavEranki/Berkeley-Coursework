(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{113:function(e,t,a){},114:function(e,t,a){},115:function(e,t,a){},116:function(e,t,a){},117:function(e,t,a){},118:function(e,t,a){},119:function(e,t,a){},121:function(e,t,a){},122:function(e,t,a){},123:function(e,t,a){"use strict";a.r(t);var n=a(48),r=a(0),o=a.n(r),c=a(15),s=a.n(c),i=(a(58),a(4)),l=a(3),u=a.n(l),m=a(11),d=a(18),p=a(19),h=a(21),f=a(20),y=a(22),g=(a(60),a(61),a(62),a(16)),v=a.n(g),E=a(10),b=a.n(E),w=a(17),_=a.n(w),W=a(49),k=a.n(W),j=a(50),T=a.n(j);function O(e){var t=e.fastestWords,a=e.playerIndex;return t.length>0&&o.a.createElement(o.a.Fragment,null,o.a.createElement("h4",null,"Fastest words typed by each player"),o.a.createElement(k.a,null,t.map(function(e,t){return o.a.createElement(_.a,null,o.a.createElement(T.a,{striped:!0,bordered:!0,hover:!0},o.a.createElement("thead",null,o.a.createElement("tr",null,o.a.createElement("th",null,"Player ",t+1,a===t&&" (you)"))),o.a.createElement("tbody",null,e.map(function(e){return o.a.createElement("tr",null,o.a.createElement("td",null,e))}))))})))}a(66),a(67);var S=function(e){function t(e){var a;return Object(d.a)(this,t),(a=Object(h.a)(this,Object(f.a)(t).call(this,e))).state={updated:!1},a}return Object(y.a)(t,e),Object(p.a)(t,[{key:"componentDidUpdate",value:function(){this.state.updated||this.setState({updated:!0})}},{key:"render",value:function(){var e="TypedWord ";return this.state.updated&&this.props.incorrect?e+="both":this.props.incorrect?e+="incorrect":this.state.updated&&(e+="updated"),o.a.createElement("span",{className:e},this.props.word," ")}}]),t}(o.a.PureComponent),C=function(e){function t(e){var a;return Object(d.a)(this,t),(a=Object(h.a)(this,Object(f.a)(t).call(this,e))).handleClick=function(){a.inputRef.current&&a.inputRef.current.focus()},a.inputRef=o.a.createRef(),a}return Object(y.a)(t,e),Object(p.a)(t,[{key:"componentDidMount",value:function(){this.inputRef.current&&this.inputRef.current.focus()}},{key:"render",value:function(){var e=this,t=this.props.words.map(function(t,a){var n=e.props.correctWords[a]!==t;return o.a.createElement(S,{key:a,word:t,incorrect:n})});return o.a.createElement("div",{className:"Input"},"And type them below:",o.a.createElement("div",{className:"InputBox",onClick:this.handleClick},t,this.props.active&&o.a.createElement(x,{ref:this.inputRef,active:this.props.active,onChange:this.props.onChange,onWordTyped:this.props.onWordTyped,popPrevWord:this.props.popPrevWord})))}}]),t}(r.Component),x=function(e){function t(e){var a;return Object(d.a)(this,t),(a=Object(h.a)(this,Object(f.a)(t).call(this,e))).handleKeyDown=function(e){8===e.keyCode&&""===e.target.innerText&&(a.setText(a.props.popPrevWord(),!1),e.preventDefault(),a.handleInput(e))},a.handleInput=function(e){var t=e.target.innerText;if(/\s/.test(t)){for(var n=t.split(/\s/),r=[],o=0;o!==n.length-1;++o){a.props.onWordTyped(n[o])||r.push(n[o])}a.setText(r.join(" ")+n[n.length-1])}else a.props.onChange(t)},a.setText=function(e,t){a.inputRef.current.innerText=e;var n=document.createRange();n.selectNodeContents(a.inputRef.current),n.collapse(t);var r=window.getSelection();r.removeAllRanges(),r.addRange(n)},a.inputRef=o.a.createRef(),a}return Object(y.a)(t,e),Object(p.a)(t,[{key:"focus",value:function(){this.inputRef.current.focus()}},{key:"render",value:function(){return o.a.createElement("span",{className:"InputField",ref:this.inputRef,contentEditable:this.props.active,onInput:this.handleInput,onKeyDown:this.handleKeyDown,onPaste:function(e){return e.preventDefault()}})}}]),t}(r.Component);a(68);function N(e){return o.a.createElement("div",{className:"Indicator"},e.text)}var I=function(){return(new Date).getTime()/1e3},L=function(e){return e?e.toFixed(1):"None"};function P(e){var t=e.wpm,a=e.accuracy,n=e.remainingTime;return o.a.createElement("div",{className:"Indicators"},o.a.createElement(N,{text:"WPM: ".concat(L(t))}),o.a.createElement(N,{text:"Accuracy: ".concat(L(a))}),o.a.createElement(N,{text:"Time: ".concat(n)}))}var R=a(7),M=a.n(R),A=(a(69),a(13)),U=a.n(A);function B(){return H.apply(this,arguments)}function H(){return(H=Object(m.a)(u.a.mark(function e(){var t,a,n,r=arguments;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return t=r.length>0&&void 0!==r[0]?r[0]:"",a=r.length>1&&void 0!==r[1]?r[1]:{},e.next=4,fetch(t,{method:"POST",mode:"same-origin",cache:"no-cache",credentials:"same-origin",headers:{"Content-Type":"application/json"},redirect:"manual",body:JSON.stringify(a)});case 4:if(200===(n=e.sent).status){e.next=7;break}throw new Error("Error ".concat(n.status,"."));case 7:return e.abrupt("return",n.json());case 8:case"end":return e.stop()}},e)}))).apply(this,arguments)}function F(e){var t=e.onNameChange,a=Object(r.useState)(""),n=Object(i.a)(a,2),c=n[0],s=n[1],l=Object(r.useState)(!1),u=Object(i.a)(l,2),m=u[0],d=u[1];Object(r.useEffect)(function(){B("/check_on_leaderboard",{user:b.a.get("user")}).then(d)},[m]);return m&&o.a.createElement(M.a.Footer,null,o.a.createElement(U.a,{onSubmit:function(e){e.preventDefault(),s(""),t(c)},style:{width:"100%"}},o.a.createElement(U.a.Row,null,o.a.createElement(_.a,null,o.a.createElement(U.a.Control,{placeholder:"Change leaderboard name",value:c,onChange:function(e){s(e.target.value)}})),o.a.createElement(v.a,{variant:"primary",type:"submit"},"Submit"))))}a(113);function D(e){return o.a.createElement("div",{className:"Entry"},o.a.createElement("span",{className:"Rank"},e.rank),o.a.createElement("span",{className:"Score"},e.score.toFixed(2)),o.a.createElement("span",{className:"Name"},e.name))}function z(e){var t=Object(r.useState)([]),a=Object(i.a)(t,2),n=a[0],c=a[1],s=function(){B("/leaderboard").then(function(e){c(e)})};return Object(r.useEffect)(function(){e.show?s():c([])},[e.show]),o.a.createElement(M.a,{size:"md","aria-labelledby":"contained-modal-title-vcenter",centered:!0,show:e.show,onHide:e.onHide},o.a.createElement(M.a.Header,{closeButton:!0},o.a.createElement(M.a.Title,{className:"Header"},"Leaderboard")),o.a.createElement(M.a.Body,null,o.a.createElement("div",{className:"Entries"},o.a.createElement("p",{id:"Title"},"Top WPMs"),n.map(function(e,t){var a=Object(i.a)(e,2),n=a[0],r=a[1];return o.a.createElement(D,{name:n,index:t,rank:t+1,score:r})}))),o.a.createElement(F,{onNameChange:function(e){B("/update_name",{newName:e,user:b.a.get("user")}).then(s)}}))}var q=a(51),G=a.n(q);a(114);function V(e){return o.a.createElement(M.a,{size:"lg","aria-labelledby":"contained-modal-title-vcenter",centered:!0,show:e.show},o.a.createElement(M.a.Body,null,o.a.createElement("div",{className:"Spinner"},o.a.createElement("p",null,"Looking for opponents..."),o.a.createElement("p",null,e.numPlayers-1," other player(s) found so far!"),o.a.createElement(G.a,{animation:"border"}))))}a(115);function K(e){return o.a.createElement(o.a.Fragment,null,o.a.createElement(J,{id:"autoCorrectCheckBox",text:"Enable Auto-Correct",value:e.autoCorrect,onChange:e.onAutoCorrectToggle}),o.a.createElement("br",null),o.a.createElement(Y,{onClick:e.onRestart}))}function J(e){return o.a.createElement("div",{className:"Options custom-control custom-checkbox"},o.a.createElement("input",{type:"checkbox",className:"custom-control-input",id:e.id,checked:e.value,onChange:e.onChange}),o.a.createElement("label",{className:"custom-control-label",htmlFor:e.id},e.text))}function Y(e){return o.a.createElement("div",{className:"Button"},o.a.createElement("button",{type:"button",className:"btn btn-primary",onClick:e.onClick},"Restart"))}a(116);function X(e){var t=function(t){return function(){e.setMode(t)}};return o.a.createElement(M.a,{size:"sm","aria-labelledby":"contained-modal-title-vcenter",centered:!0,show:e.show},o.a.createElement(M.a.Header,null,o.a.createElement(M.a.Title,null,"Welcome!")),o.a.createElement(M.a.Body,null,o.a.createElement("p",null,"Welcome to the 61A Typing Test!"),o.a.createElement("p",null,"Select a mode below to begin.")),o.a.createElement(M.a.Footer,null,o.a.createElement(v.a,{onClick:t(se.SINGLE),variant:"primary"},"Single Player"),o.a.createElement(v.a,{onClick:t(se.WAITING),variant:"warning"},"Multiplayer")))}a(117),a(118);function Q(e){var t="Character ";return e.correct?t+="correct":e.wrong&&(t+="wrong"),o.a.createElement("span",{className:t},e.char)}function Z(e){for(var t=e.promptedWords,a=e.typedWords,n=e.currWord,r=[],c=!1,s=0;s!==t.length;++s){var i=t[s],l=a[s];if(l){var u=i===l,m=!0,d=!1,p=void 0;try{for(var h,f=i[Symbol.iterator]();!(m=(h=f.next()).done);m=!0){var y=h.value;r.push(o.a.createElement(Q,{key:r.length,char:y,correct:u,wrong:!u}))}}catch(T){d=!0,p=T}finally{try{m||null==f.return||f.return()}finally{if(d)throw p}}r.push(o.a.createElement(Q,{key:r.length,char:" "}))}else if(c){var g=!0,v=!1,E=void 0;try{for(var b,w=i[Symbol.iterator]();!(g=(b=w.next()).done);g=!0){var _=b.value;r.push(o.a.createElement(Q,{key:r.length,char:_}))}}catch(T){v=!0,E=T}finally{try{g||null==w.return||w.return()}finally{if(v)throw E}}r.push(o.a.createElement(Q,{key:r.length,char:" "}))}else{for(var W=0;W!==i.length;++W){var k=n[W]&&i[W]===n[W],j=n[W]&&i[W]!==n[W];r.push(o.a.createElement(Q,{key:r.length,char:i[W],correct:k,wrong:j}))}r.push(o.a.createElement(Q,{key:r.length,char:" "})),c=!0}}return o.a.createElement("div",{className:"PromptBox"},"Look at the following words:",o.a.createElement("div",{className:"Prompt"},r))}a(119);var $=a(52),ee=a.n($);function te(e){var t=e.progress,a=e.playerIndex,n=["info","warning","success","danger"];return o.a.createElement("div",{className:"ProgressBars"},t.map(function(e,t){var r=Object(i.a)(e,2),c=r[0],s=r[1],l=1===c&&"".concat(s.toFixed(2)," seconds"),u=t===a?" (you)":"";return o.a.createElement("div",{className:"ProgressBar",key:t},o.a.createElement(ee.a,{variant:n[t],animated:!0,label:"Player ".concat(t+1).concat(u),now:100*c}),o.a.createElement("div",{className:"barData"},l))}))}a(121);function ae(e){var t=e.images,a=e.lastWordLen,n=e.onSubmit,c=Object(r.useState)([]),s=Object(i.a)(c,2),l=s[0],d=s[1],p=Object(r.useState)(""),h=Object(i.a)(p,2),f=h[0],y=h[1],g=Object(r.useState)(I()),v=Object(i.a)(g,1)[0],E=Object(r.useState)(null),b=Object(i.a)(E,2),w=b[0],_=b[1],W=Object(r.useState)(!0),k=Object(i.a)(W,2),j=k[0],T=k[1],O=function(){var e=Object(m.a)(u.a.mark(function e(r){var o,c,s;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return o=l.join(" "),e.next=3,B("/analyze",{promptedText:o,typedText:o,startTime:v,endTime:I()});case 3:c=e.sent,s=c.wpm,_(s),y(r),l.length+1===t.length&&r&&r.length===a&&(d(l.concat([r])),y(""),T(!1),n(l.concat([r])));case 8:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}();!function(e,t){var a=Object(r.useRef)();Object(r.useEffect)(function(){a.current=e},[e]),Object(r.useEffect)(function(){if(null!==t){var e=setInterval(function(){a.current()},t);return function(){return clearInterval(e)}}},[t])}(O,100);return o.a.createElement("div",{className:"CaptchaChallenge"},"Look at the following words:",o.a.createElement("div",{className:"images"},t.map(function(e,t){return o.a.createElement("img",{className:t===l.length?"activeImage":"",src:e,key:t})})),o.a.createElement("br",null),o.a.createElement(C,{correctWords:l,words:l,onWordTyped:function(e){return""===e||(y(""),d(l.concat([e])),!0)},onChange:O,popPrevWord:function(){return l.length?(d(l.slice(0,l.length-1)),l[l.length-1]):""},active:j}),o.a.createElement("br",null),o.a.createElement("div",{className:"form-group"},o.a.createElement("button",{onClick:function(){n(l.concat([f]))},type:"submit",className:"btn btn-primary"},"Submit"),o.a.createElement(N,{text:"WPM: ".concat(L(w))})))}function ne(e){var t=e.message,a=e.onClick,n=t||"However, you first need to complete this Captcha challenge to validate your WPM. Click the button to receive your challenge.";return o.a.createElement(o.a.Fragment,null,o.a.createElement("p",null,"Congratulations! Your WPM is fast enough to place on our leaderboard!"),o.a.createElement("p",null,n),o.a.createElement("div",{className:"form-group",onClick:a},o.a.createElement("button",{type:"button",className:"btn btn-primary"},"Request Challenge")),o.a.createElement("small",{id:"emailHelp",className:"form-text text-muted"},"You'll need to type at a speed similar to your current WPM to pass the check. It's OK if you make mistakes or type a bit slower. After you pass the challenge, you won't be asked again for some time."))}function re(e){var t=e.onSubmit,a=Object(r.useRef)(null);return o.a.createElement(o.a.Fragment,null,"Congratulations! Your WPM is fast enough to place on our leaderboard! Enter a name here to associate it with your score:",o.a.createElement("br",null),o.a.createElement("form",{onSubmit:function(e){e.preventDefault(),t(a.current.value)}},o.a.createElement("div",{className:"form-group"},o.a.createElement("input",{type:"text",ref:a,className:"form-control",id:"exampleInputEmail1","aria-describedby":"emailHelp",placeholder:"Enter username"}),o.a.createElement("small",{id:"emailHelp",className:"form-text text-muted"},"Please don't name yourself anything inappropriate!")),o.a.createElement("div",{className:"form-group"},o.a.createElement("button",{type:"submit",className:"btn btn-primary"},"Submit"))))}function oe(e){var t=e.show,a=e.onHide,n=e.needVerify,c=e.wpm,s=e.onSubmit,l=Object(r.useState)([]),d=Object(i.a)(l,2),p=d[0],h=d[1],f=Object(r.useState)([]),y=Object(i.a)(f,2),g=y[0],v=y[1],E=Object(r.useState)(""),w=Object(i.a)(E,2),_=w[0],W=w[1],k=Object(r.useState)(!n),j=Object(i.a)(k,2),T=j[0],O=j[1],S=Object(r.useRef)(null);!t&&p.length&&h([]);var C=function(){var e=Object(m.a)(u.a.mark(function e(){var t,a,n,r;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,B("/request_wpm_challenge",{user:b.a.get("user")});case 2:t=e.sent,a=t.images,n=t.token,r=t.lastWordLen,h(a),v(r),S.current=n;case 9:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),x=function(){var e=Object(m.a)(u.a.mark(function e(t){var a,n,r,o;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,B("/claim_wpm_challenge",{user:b.a.get("user"),token:S.current,typed:t,claimedWpm:c});case 2:a=e.sent,n=a.success,r=a.message,o=a.token,n?(O(!0),b.a.set("token",o)):(W("The server said: ".concat(r," Please try again.")),h([]));case 7:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}(),N=p.length?o.a.createElement(ae,{images:p,lastWordLen:g,onSubmit:x}):o.a.createElement(ne,{message:_,onClick:C}),I=T?o.a.createElement(re,{onSubmit:s}):N;return o.a.createElement(M.a,{size:"md","aria-labelledby":"contained-modal-title-vcenter",centered:!0,show:t,onHide:a},o.a.createElement(M.a.Header,{closeButton:!0},o.a.createElement(M.a.Title,{className:"Header"},"High Score")),o.a.createElement(M.a.Body,null,I))}a(122);function ce(e){var t=e.onClick,a=Object(r.useState)(""),n=Object(i.a)(a,2),c=n[0],s=n[1],l=function(){t(c.split(/\s|,/).map(function(e){return e.trim().toLowerCase()}).filter(function(e){return e.length}))};return o.a.createElement("div",{className:"TopicPicker"},o.a.createElement(U.a,{onSubmit:function(e){e.preventDefault(),l()}},o.a.createElement(U.a.Label,null,"Specify topics of interest"),o.a.createElement(U.a.Row,null,o.a.createElement(_.a,null,o.a.createElement(U.a.Control,{placeholder:"Cat, Cats, Kittens, ...",value:c,onChange:function(e){s(e.target.value)}})),o.a.createElement(v.a,{variant:"primary",onClick:l},"Submit")),o.a.createElement(U.a.Text,{className:"text-muted"},"List topics separated by commas.")))}var se={SINGLE:"single",MULTI:"multi",WELCOME:"welcome",WAITING:"waiting"},ie=function(e){function t(e){var a;return Object(d.a)(this,t),(a=Object(h.a)(this,Object(f.a)(t).call(this,e))).initialize=function(){a.setState({typedWords:[],currWord:"",inputActive:!0,wpm:null,accuracy:null,fastestWords:[]}),B("/request_paragraph",{topics:a.state.topics}).then(function(e){a.state.pigLatin?B("/translate_to_pig_latin",{text:e}).then(function(e){a.setState({promptedWords:e.split(" ")})}):a.setState({promptedWords:e.split(" ")})}),a.setState({startTime:0,currTime:0}),clearInterval(a.timer),a.timer=null},a.restart=function(){a.timer=setInterval(a.updateReadouts,100),a.setState({startTime:I(),currTime:I()})},a.updateReadouts=Object(m.a)(u.a.mark(function e(){var t,n,r,o,c;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return t=a.state.promptedWords.join(" "),n=a.state.typedWords.join(" "),e.next=4,B("/analyze",{promptedText:t,typedText:n,startTime:a.state.startTime,endTime:I()});case 4:return r=e.sent,o=r.wpm,c=r.accuracy,a.setState({wpm:o,accuracy:c,currTime:I()}),e.abrupt("return",{wpm:o,accuracy:c});case 9:case"end":return e.stop()}},e)})),a.reportProgress=function(){var e=a.state.promptedWords.join(" ");B("/report_progress",{id:a.state.id,typed:a.state.typedWords.join(" "),prompt:e})},a.requestProgress=Object(m.a)(u.a.mark(function e(){var t;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,B("/request_progress",{targets:a.state.playerList});case 2:t=e.sent,a.setState({progress:t}),t.every(function(e){return 1===Object(i.a)(e,1)[0]})&&(clearInterval(a.multiplayerTimer),a.fastestWords());case 5:case"end":return e.stop()}},e)})),a.fastestWords=Object(m.a)(u.a.mark(function e(){var t;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,B("/fastest_words",{targets:a.state.playerList,prompt:a.state.promptedWords.join(" ")});case 2:t=e.sent,a.setState({fastestWords:t});case 4:case"end":return e.stop()}},e)})),a.popPrevWord=function(){if(0!==a.state.typedWords.length){var e=a.state.typedWords[a.state.typedWords.length-1];return a.setState(function(e){return{typedWords:e.typedWords.slice(0,e.typedWords.length-1)}}),e}return""},a.handleWordTyped=function(e){if(!e)return!0;var t=a.state.typedWords.length;return a.setState(function(n){return n.autoCorrect&&e!==n.promptedWords[t]&&B("/autocorrect",{word:e}).then(function(n){a.setState(function(a){if(a.typedWords[t]!==e)return{};var r=a.typedWords;return r[t]=n,{typedWords:r}})}),{typedWords:n.typedWords.concat([e]),currWord:""}},function(){a.updateReadouts(),a.state.mode===se.MULTI&&a.reportProgress()}),!0},a.handleChange=function(){var e=Object(m.a)(u.a.mark(function e(t){var n,r,o,c,s,i;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(a.setState({currWord:t}),a.state.typedWords.length+1!==a.state.promptedWords.length||a.state.promptedWords[a.state.promptedWords.length-1]!==t||a.state.mode!==se.SINGLE&&a.state.typedWords.concat([t]).join(" ")!==a.state.promptedWords.join(" ")){e.next=18;break}return clearInterval(a.timer),a.setState({inputActive:!1}),a.handleWordTyped(t),n=b.a.get("token")||null,e.next=8,B("/check_leaderboard_eligibility",{user:b.a.get("user"),wpm:a.state.wpm,token:n});case 8:return r=e.sent,o=r.eligible,c=r.needVerify,e.next=13,a.updateReadouts();case 13:s=e.sent,i=s.wpm,a.setState({wpm:i},function(){o&&100===a.state.accuracy&&a.setState({showUsernameEntry:!0,needVerify:c})}),e.next=19;break;case 18:a.timer||a.restart();case 19:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}(),a.handlePigLatinToggle=function(){a.initialize(),a.setState(function(e){return{autoCorrect:!1,pigLatin:!e.pigLatin}})},a.handleAutoCorrectToggle=function(){a.initialize(),a.setState(function(e){return{autoCorrect:!e.autoCorrect,pigLatin:!1}})},a.setMode=function(e){a.setState({mode:e}),e===se.WAITING&&(a.multiplayerTimer=setInterval(a.requestMatch,1e3))},a.requestMatch=Object(m.a)(u.a.mark(function e(){var t;return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,B("/request_match",{id:a.state.id});case 2:(t=e.sent).start?(a.setState({mode:se.MULTI,playerList:t.players,numPlayers:t.players.length,promptedWords:t.text.split(" "),progress:new Array(t.players.length).fill([0,0]),pigLatin:!1,autoCorrect:!1}),clearInterval(a.multiplayerTimer),a.multiplayerTimer=setInterval(a.requestProgress,500)):a.setState({numPlayers:t.numWaiting});case 4:case"end":return e.stop()}},e)})),a.toggleLeaderBoard=function(){a.setState(function(e){return{showLeaderboard:!e.showLeaderboard}})},a.handleSetTopics=function(e){a.setState({topics:e},a.initialize)},a.handleUsernameSubmission=function(){var e=Object(m.a)(u.a.mark(function e(t){return u.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,B("/record_wpm",{name:t,user:b.a.get("user"),wpm:a.state.wpm,token:b.a.get("token")||null});case 2:a.hideUsernameEntry();case 3:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}(),a.hideUsernameEntry=function(){a.setState({showUsernameEntry:!1})},a.state={promptedWords:["Please wait - loading!"],typedWords:[],wpm:null,accuracy:null,startTime:0,currTime:0,pigLatin:!1,autoCorrect:!1,currWord:"",inputActive:!1,numPlayers:1,mode:se.SINGLE,playerList:[],progress:[],showLeaderboard:!1,fastestWords:[],showUsernameEntry:!1,needVerify:!1,topics:[]},a.timer=null,a.multiplayerTimer=null,B("/request_id").then(function(e){null!==e&&a.setState({id:e.toString(),mode:se.WELCOME})}),b.a.get("user")||b.a.set("user",function(e){for(var t="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",a="",n=0;n<e;n++){var r=Math.floor(Math.random()*t.length);a+=t.substring(r,r+1)}return a}(32)),a}return Object(y.a)(t,e),Object(p.a)(t,[{key:"componentDidMount",value:function(){this.initialize()}},{key:"componentDidUpdate",value:function(){this.state.mode===se.WELCOME||this.state.mode===se.WAITING||this.state.showLeaderboard?document.getElementById("app-root").style.filter="blur(5px)":document.getElementById("app-root").style.filter="none"}},{key:"componentWillUnmount",value:function(){clearInterval(this.timer),clearInterval(this.multiplayerTimer)}},{key:"render",value:function(){var e=this,t=this.state,a=t.wpm,n=t.accuracy,r=t.numPlayers,c=t.startTime,s=t.currTime,i=t.playerList,l=t.id,u=t.fastestWords,m=(s-c).toFixed(1),d=i.indexOf(l);return o.a.createElement(o.a.Fragment,null,o.a.createElement("div",{className:"App container",id:"app-root"},o.a.createElement("div",{className:"row"},o.a.createElement("div",{className:"col"},o.a.createElement("br",null),o.a.createElement("div",{className:"LeaderboardButton"},o.a.createElement(v.a,{onClick:function(){return e.toggleLeaderBoard(!1)},variant:"outline-dark"},"Leaderboard")),o.a.createElement("h1",{className:"display-4 mainTitle"},o.a.createElement("b",null,"C"),"S61A ",o.a.createElement("b",null,"A"),"utocorrected ",o.a.createElement("b",null,"T"),"yping ",o.a.createElement("b",null,"S"),"oftware"),o.a.createElement("br",null),o.a.createElement(P,{wpm:a,accuracy:n,remainingTime:m}),this.state.mode===se.MULTI&&o.a.createElement(te,{numPlayers:r,progress:this.state.progress,playerIndex:d}),o.a.createElement("br",null),o.a.createElement(Z,{promptedWords:this.state.promptedWords,typedWords:this.state.typedWords,currWord:this.state.currWord}),o.a.createElement("br",null),o.a.createElement(C,{key:this.state.promptedWords[0],correctWords:this.state.promptedWords,words:this.state.typedWords,onWordTyped:this.handleWordTyped,onChange:this.handleChange,popPrevWord:this.popPrevWord,active:this.state.inputActive}),o.a.createElement("br",null),this.state.mode!==se.MULTI&&o.a.createElement(o.a.Fragment,null,o.a.createElement(K,{pigLatin:this.state.pigLatin,onPigLatinToggle:this.handlePigLatinToggle,autoCorrect:this.state.autoCorrect,onAutoCorrectToggle:this.handleAutoCorrectToggle,onRestart:this.initialize}),o.a.createElement("br",null),o.a.createElement(ce,{onClick:this.handleSetTopics})),this.state.mode===se.MULTI&&o.a.createElement(O,{playerIndex:d,fastestWords:u})))),o.a.createElement(X,{show:this.state.mode===se.WELCOME,setMode:this.setMode,toggleFindingOpponents:this.toggleFindingOpponents}),o.a.createElement(V,{show:this.state.mode===se.WAITING,numPlayers:this.state.numPlayers}),o.a.createElement(z,{show:this.state.showLeaderboard,onHide:this.toggleLeaderBoard}),o.a.createElement(oe,{key:this.state.showUsernameEntry,wpm:this.state.wpm,show:this.state.showUsernameEntry,needVerify:this.state.needVerify,onHide:this.hideUsernameEntry,onSubmit:this.handleUsernameSubmission}))}}]),t}(r.Component);Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));function le(){var e=Object(n.a)(["\n _________________________________________ \n/ Hello adventurous student! If you want  | to see the source of the GUI, go to Dev |\n| Tools => Sources => Page => top =>      |\n| [this domain] => static => js and       |\n enjoy!                                  /\n ----------------------------------------- \n                                  _\n                            |                             | |\n                             | |\n        |                   | |\n       /, ~                / /\n      X     `-.....-------./ /\n       ~-. ~  ~              |\n                       /    |\n             /_     ___   /\n           | / ~~~~~    |\n           | |         || |\n           | |        || )\n          (_/ (_/      ((_/\n"],["\n _________________________________________ \n/ Hello adventurous student! If you want  \\\n| to see the source of the GUI, go to Dev |\n| Tools => Sources => Page => top =>      |\n| [this domain] => static => js and       |\n\\ enjoy!                                  /\n ----------------------------------------- \n  \\\n   \\\n    \\                         _\n     \\                       | \\\n      \\                      | |\n                             | |\n        |\\                   | |\n       /, ~\\                / /\n      X     \\`-.....-------./ /\n       ~-. ~  ~              |\n          \\             /    |\n           \\  /_     ___\\   /\n           | /\\ ~~~~~   \\ |\n           | | \\        || |\n           | |\\ \\       || )\n          (_/ (_/      ((_/\n"]);return le=function(){return e},e}document.body.prepend(new Comment(String.raw(le()))),B("/favicon").then(function(e){document.querySelector('link[rel="shortcut icon"]').href=e}),s.a.render(o.a.createElement(ie,null),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then(function(e){e.unregister()})},53:function(e,t,a){e.exports=a(123)},58:function(e,t,a){},60:function(e,t,a){},66:function(e,t,a){},67:function(e,t,a){},68:function(e,t,a){},69:function(e,t,a){}},[[53,1,2]]]);
//# sourceMappingURL=main.47dbb5e1.chunk.js.map