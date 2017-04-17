(function (window, undefined) {
    var
        document = window.document,

        seven = function (selector, context) {
            return new seven.fn.init(selector, context);
        };

    seven.fn = seven.prototype = {
        constructor: seven,

        init: function (selector, context) {
            if (!selector) {
                return this;
            }

            var self = this;
            self.elements = [];
            var match, elem, length;
            self.tween = {
                linear: function (t, b, c, d) {
                    return t * c / d + b;
                },
                easeIn: function (t, b, c, d) {
                    return c * (t /= d) * t + b;
                },
                elasticOut: function (t, b, c, d, a, p) {
                    if (t == 0) return b;
                    if ((t /= d) == 1) return b + c;
                    if (!p) p = d * .3;
                    if (!a || a < Math.abs(c)) {
                        a = c;
                        var s = p / 4;
                    }
                    else var s = p / (2 * Math.PI) * Math.asin(c / a);
                    return (a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b);
                }
            }

            switch (typeof selector) {
                case 'function':
                    self.bindEvent(window, 'load', selector);
                    break;
                case 'string':
                    if (selector.split(/\s+/g) && selector.split(/\s+/g).length > 1) {
                        /*css*/
                        var _elements = selector.split(/\s+/);
                        var _subElements = [];
                        var _node = [];
                        /*存放父节点*/
                        for (var i = 0; i < _elements.length; i++) {
                            if (typeof _elements[i] === 'string' && _elements[i].replace(/\s+/) != '') {
                                if (_node.length === 0) _node.push(document);
                                switch (_elements[i].charAt(0)) {
                                    case '#':/*id*/
                                        _subElements = [];
                                        /*清空临时节点*/
                                        _subElements = (self.getId(_elements[i].substring(1), document));
                                        _node = _subElements;
                                        break;
                                    case '.':/*class*/
                                        _subElements = [];
                                        for (var j = 0; j < _node.length; j++) {
                                            var tmpElements = self.getClass(_elements[i].substring(1), _node[j]);
                                            for (var x = 0; x < tmpElements.length; x++) {
                                                _subElements.push(tmpElements[x]);
                                            }
                                        }
                                        _node = _subElements;
                                        break;
                                    default:/*tag*/
                                        _subElements = [];
                                        for (var j = 0; j < _node.length; j++) {
                                            var tmpElements = self.getTag(_elements[i], _node[j]);
                                            for (var x = 0; x < tmpElements.length; x++) {
                                                _subElements.push(tmpElements[x]);
                                            }
                                        }
                                        _node = _subElements;
                                        break;
                                }
                            }
                        }
                        self.elements = _subElements;
                    } else {
                        /*find*/
                        switch (selector.charAt(0)) {
                            case '#':/*id*/
                                self.elements = self.getId(selector.substring(1));
                                break;
                            case '.':/*class*/
                                self.elements = self.getClass(selector.substring(1));
                                break;
                            default:/*tag*/
                                self.elements = self.getTag(selector);
                                break;
                        }
                    }
                    break;
                case 'object':
                    self.elements.push(selector);
                    break;
                default:
                    break
            }
            self.length = self.elements.length;
            return self;
        },
        /*internal function*/
        initialize: function (args1, args2) {
            for (var attr in args2) {
                args1[attr] = args2[attr];
            }
        },
        /*internal functions*/
        bindEvent: function (obj, events, fn) {
            if (typeof obj.events == 'undefined') obj.events = [];
            var ev = {};
            ev.name = events;
            ev.fn = fn;
            obj.events.push(ev);
            if (obj.addEventListener) {
                obj.addEventListener(events, fn, false);
            } else {
                obj.addachEvent('on' + events, fn);
            }
        },
        /*internal functions*/
        removeEvent: function (obj, events) {
            if (obj.events) {
                for (var i = 0; i < obj.events.length; i++) {
                    if (obj.events[i].name == events) {
                        if (obj.removeEventListener)
                            obj.removeEventListener(events, obj.events[i].fn, false);
                        else if (obj.detachEvent)
                            obj.detachEvent("on" + events, obj.events[i].fn);
                        else obj["on" + events] = null;
                    }
                }
            }
        },
        /*internal functions*/
        getId: function (id, parent) {
            if (!parent) parent = document;
            var tmpElements = [];
            tmpElements.push(parent.getElementById(id));
            return tmpElements;
        },
        /*internal functions*/
        getClass: function (className, parent) {
            if (!parent) parent = document;
            var all = null;
            if (parent.getElementsByClassName) {    //no lower than IE9
                all = parent.getElementsByClassName(className);
            } else {
                all = parent.getElementsByTagName('*');
            }
            var tmpElements = [];
            var index = className.indexOf(':');
            if (index === -1) {
                for (var i = 0; i < all.length; i++) {
                    if (all[i].className.match(className)) {
                        tmpElements.push(all[i]);
                    }
                }
            } else {
                var _className = className.substring(0, index);
                _className = _className.replace(/^\s+|\s+$/, '');
                var subSelector = className.substring(index + 1);
                switch (subSelector) {
                    case 'first':
                        for (var i = 0; i < all.length; i++) {
                            if (all[i].className.match(_className)) {
                                tmpElements.push(all[i]);
                                break;
                            }
                        }
                        break;
                    case 'last':
                        for (var i = 0; i < all.length; i++) {
                            if (all[i].className.match(_className)) {
                                tmpElements.push(all[i]);
                            }
                        }
                        var els = [];
                        if (tmpElements.length > 0) {
                            els.push(tmpElements[tmpElements.length - 1]);
                        }
                        tmpElements = els;
                        break;
                }
            }

            return tmpElements;
        },
        /*internal function*/
        getTag: function (tag, parent) {
            if (!parent) parent = document;
            var self = this;
            var tags = null;
            var flag = 0;
            var tmpElements = [];
            var tIndex = 0; // index有可能会被覆盖，此处需要另定义一个变量保存index
            //
            var index = tag.indexOf(':');
            if (index != -1) {
                flag = 1;
                tIndex = index;
            }
            //
            index = tag.indexOf('[');
            if (index != -1) {
                flag = 2;
                tIndex = index;
            }
            if (flag === 0) {
                tags = parent.getElementsByTagName(tag);
                for (var i = 0; i < tags.length; i++) {
                    tmpElements.push(tags[i]);
                }

                return tmpElements;
            }
            else if (flag === 1) {
                var _tag = tag.substring(0, tIndex);
                _tag = _tag.replace(/^\s+|\s+$/, '');
                if (_tag.replace(/\s+/, '').length == 0) { // tag = :first
                    tags = self.elements;
                } else {
                    tags = parent.getElementsByTagName(_tag);
                }
                //console.log(Date.now())
                var subSelector = tag.substring(tIndex + 1);
                //console.log(subSelector);
                switch (subSelector) {
                    case 'first':
                        for (var i = 0; i < tags.length; i++) {
                            tmpElements.push(tags[i]);
                            break;
                        }
                        break;
                    case 'last':
                        for (var i = 0; i < tags.length; i++) {
                            tmpElements.push(tags[i]);
                        }
                        var els = [];
                        if (tmpElements.length > 0) {
                            els.push(tmpElements[tmpElements.length - 1]);
                        }
                        tmpElements = els;
                        break;
                    case 'checked':
                        for (var i = 0; i < tags.length; i++) {
                            //console.log(tags[i].checked);
                            if (tags[i].checked) {
                                tmpElements.push(tags[i]);
                            }
                        }
                        break;

                    default:
                        break;
                }
            }
            else if (flag === 2) {
                var _tag = tag.substring(0, index); // input[name='userName']    get _tag = input
                var _end = tag.indexOf(']');
                var _condition = tag.substring(index + 1, _end);    // get name='userName'
                _condition = _condition.replace(/(\')|(\")/g, '');
                // console.log(_condition);

                var _arr = _condition.split('=');   // _arr[0]=name, _arr[1]=userName
                if (_arr.length != 2) return tmpElements;
                //
                tags = parent.getElementsByTagName(_tag);   // all input
                for (var i = 0; i < tags.length; i++) {
                    if (tags[i].getAttribute(_arr[0]) === _arr[1]) {
                        tmpElements.push(tags[i]);
                    }
                }

                self.elements = tmpElements;
                index = tag.indexOf(':');   // input[name='id']:checked
                if (index != -1) {
                    tmpElements = self.getTag(tag.substring(index)) // tag = :checked
                }
            }

            return tmpElements;
        },
        find: function (args) {
            var self = this;
            var subElement = [];
            for (var i = 0; i < self.elements.length; i++) {
                switch (args.charAt(0)) {
                    case '#':
                        self.getId(args.substring(1));
                        break;
                    case '.':
                        var tmpElements = self.getClass(args.substring(1), self.elements[i]);
                        for (var j = 0; j < tmpElements.length; j++) {
                            subElement.push(tmpElements[j]);
                        }
                        break;
                    default:
                        var tmpElements = self.getTag(args, self.elements[i]);
                        for (var j = 0; j < tmpElements.length; j++) {
                            subElement.push(tmpElements[j]);
                        }
                        break;
                }
            }
            self.elements = subElement;

            return self;
        },
        siblings: function () {
            var currentNote = this.elements[0];
            this.elements = currentNote.parentNode.children;

            return this;
        },
        is: function (state) {
            var self = this;
            state = state.replace(/\s+/g, '');
            state = state.substring(1); // :checked     remove ':'
            switch (state) {
                case 'checked':
                    return self.elements[0].checked;
                    break;

                default:
                    break;
            }
        },
        /*internal function*/
        getStyle: function (obj, attr) {
            if (typeof window.getComputedStyle != 'undefined') {/*W3C*/
                return window.getComputedStyle(obj, null)[attr];
            } else if (typeof obj.currentStyle != 'undefined') {/*IE*/
                return obj.currentStyle[attr];
            }
        },
        css: function (attr, value) {
            if (typeof attr == 'string') {
                for (var i = 0; i < this.elements.length; i++) {
                    if (arguments.length == 1) {
                        return this.getStyle(this.elements[i], attr);
                    }
                    this.elements[i].style[attr] = value;
                }
            } else if (typeof attr == 'object') {
                for (var i = 0; i < this.elements.length; i++) {
                    for (var key in attr) {
                        var value = attr[key];
                        this.elements[i].style[key] = value;
                    }
                }
            }

            return this;
        },
        attr: function (attr, value) {
            if (typeof attr == 'string') {
                for (var i = 0; i < this.elements.length; i++) {
                    if (arguments.length == 1) {
                        return this.elements[i].getAttribute(attr);
                    }
                    this.elements[i].setAttribute(attr, value);
                }
            } else if (typeof attr == 'object') {
                for (var i = 0; i < this.elements.length; i++) {
                    for (var key in attr) {
                        var value = attr[key];
                        this.elements[i].setAttribute(key, value);
                    }
                }
            }

            return this;
        },
        removeAttr: function (attr) {
            if (typeof attr == 'string') {
                for (var i = 0; i < this.elements.length; i++) {
                    this.elements[i].removeAttribute(attr);
                }
            } else if (typeof attr == 'object') {
                for (var i = 0; i < this.elements.length; i++) {
                    for (var key in attr) {
                        this.elements[i].removeAttribute(key);
                    }
                }
            }

            return this;
        },
        html: function (str) {
            for (var i = 0; i < this.elements.length; i++) {
                if (arguments.length == 0) {
                    return this.elements[i].innerHTML;
                }
                this.elements[i].innerHTML = str;
            }
            return this;
        },
        text: function (str) {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].innerText) {
                    if (arguments.length == 0) {
                        return this.elements[i].innerText;
                    }
                    this.elements[i].innerText = str;
                } else {
                    if (arguments.length == 0) {
                        return this.elements[i].textContent;
                    }
                    this.elements[i].textContent = str;
                }
            }
            return this;
        },
        val: function (str) {
            for (var i = 0; i < this.elements.length; i++) {
                if (arguments.length == 0) {
                    return this.elements[i].value;
                }
                this.elements[i].value = str;
            }
            return this;
        },
        empty: function () {
            this.val('');
            this.text('');
            this.html('');

            return this;
        },
        top: function () {
            var top = this.elements[0].offsetTop;
            var parent = this.elements[0].offsetParent;
            while (parent != null) {
                top += parent.offsetTop;
                parent = parent.offsetParent;
            }
            return top;
        },
        left: function () {
            var left = this.elements[0].offsetLeft;
            var parent = this.elements[0].offsetParent;
            while (parent != null) {
                left += parent.offsetLeft;
                parent = parent.offsetParent;
            }
            return left;
        },
        width: function (size) {
            if (size) {
                for (var i = 0; i < this.elements.length; i++) {
                    if (arguments.length == 0) {
                        return parseInt(this.elements[i].offsetWidth);
                    }
                    this.elements[i].style.width = size + 'px';
                }
                return this;
            } else {
                return this.innerWidth();
            }
        },
        height: function (size) {
            if (size) {
                for (var i = 0; i < this.elements.length; i++) {
                    if (arguments.length == 0) {
                        return parseInt(this.elements[i].offsetHeight);
                    }
                    this.elements[i].style.height = size + 'px';
                }

                return this;
            } else {
                return this.innerHeight();
            }
        },
        innerWidth: function () {
            if (typeof this.elements[0] == 'undefined') {
                this.elements.push(window);
            }
            if (this.elements[0].innerWidth)
                return this.elements[0].innerWidth;
            else
                return this.elements[0].offsetWidth;
        },
        innerHeight: function () {
            if (typeof this.elements[0] == 'undefined') {
                this.elements.push(window);
            }
            if (this.elements[0].innerHeight)
                return this.elements[0].innerHeight;
            else
                return this.elements[0].offsetHeight;
        },
        scrollTop: function () {
            return document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
        },
        offset: function () {
            return {
                top: this.elements[0].offsetTop,
                left: this.elements[0].offsetLeft,
                width: this.elements[0].offsetWidth,
                height: this.elements[0].offsetHeight
            }
        },
        scrollHeight: function () {
            return document.body.scrollHeight;
        },
        scrollLeft: function () {
            return document.body.scrollLeft;
        },
        append: function (obj) {
            for (var i = 0; i < this.elements.length; i++) {
                this.elements[i].appendChild(obj);
            }
            return this;
        },
        parent: function () {
            this.elements = [];
            this.push(this.elements[0].parentNode);
            return this;
        },
        focus: function () {
            this.elements[0].focus();
            return this;
        },
        size: function () {
            return this.elements.length;
        },
        prev: function (tag) {
            var tmpElements = [];
            var obj = this.elements[0];
            var parent = obj.parentNode;
            var objArray = parent.getElementsByTagName('*');
            if (!objArray) {
                this.elements = tmpElements;
                return this;
            }
            if (!tag) {
                for (var i = 0; i < objArray.length; i++) {
                    if (objArray[i] == obj) {
                        this.push(objArray[i - 1]);
                        return this;
                    }
                }
            } else {
                tag = tag.toLowerCase();
                for (var i = 0; i < objArray.length; i++) {
                    var tagName = objArray[i].tagName.toLowerCase();
                    if (tagName == tag) {
                        tmpElements.push(objArray[i]);
                    }
                    if (objArray[i] == obj) {
                        if (tagName == tag) {
                            this.push(tmpElements[tmpElements.length - 2]);
                        } else {
                            if (tmpElements.length > 0) {
                                this.push(tmpElements[tmpElements.length - 1]);
                            } else {
                                this.elements = tmpElements;
                            }
                        }

                        return this;
                    }
                }
            }

            this.elements = tmpElements;
            return this;
        },
        prevAll: function (tag) {
            var tmpElements = [];
            var obj = this.elements[0];
            var parent = obj.parentNode;
            var objArray = parent.getElementsByTagName('*');
            if (!objArray) {
                this.elements = tmpElements;
                return this;
            }
            if (!tag) {
                for (var i = 0; i < objArray.length; i++) {
                    tmpElements.push(objArray[i]);
                    if (objArray[i] == obj) {
                        tmpElements.pop();
                        this.elements = tmpElements;
                        return this;
                    }
                }
            } else {
                tag = tag.toLowerCase();
                for (var i = 0; i < objArray.length; i++) {
                    var tagName = objArray[i].tagName.toLowerCase();
                    if (tagName == tag) {
                        tmpElements.push(objArray[i]);
                    }
                    if (objArray[i] == obj) {
                        if (tagName == tag) {
                            tmpElements.pop();
                        }
                        this.elements = tmpElements;
                        return this;
                    }
                }
            }

            this.elements = tmpElements;
            return this;
        },
        next: function (tag) {
            var tmpElements = [];
            var obj = this.elements[0];
            var parent = obj.parentNode;
            var objArray = parent.getElementsByTagName('*');
            var index = 0;
            if (!objArray) {
                this.elements = tmpElements;
                return this;
            }
            if (!tag) {
                for (var i = 0; i < objArray.length; i++) {
                    if (index > 0) {
                        this.push(objArray[i]);
                        return this;
                    }
                    if (objArray[i] == obj) {
                        index = i;
                    }
                }
                this.elements = tmpElements;
                return this;
            } else {
                tag = tag.toLowerCase();
                for (var i = 0; i < objArray.length; i++) {
                    var tagName = objArray[i].tagName.toLowerCase();
                    if (index > 0) {
                        if (tagName == tag) {
                            this.push(objArray[i]);
                            return this;
                        }
                    }
                    if (objArray[i] == obj) {
                        index = i;
                    }
                }
                this.elements = tmpElements;
                return this;
            }
            this.elements = tmpElements;
            return this;
        },
        nextAll: function (tag) {
            var tmpElements = [];
            var obj = this.elements[0];
            var parent = obj.parentNode;
            var objArray = parent.getElementsByTagName('*');
            var index = 0;
            if (!objArray) {
                this.elements = tmpElements;
                return this;
            }
            if (!tag) {
                for (var i = 0; i < objArray.length; i++) {
                    if (index > 0) {
                        tmpElements.push(objArray[i]);
                    }
                    if (objArray[i] == obj) {
                        index = i;
                    }
                }
                this.elements = tmpElements;
                return this;
            } else {
                tag = tag.toLowerCase();
                for (var i = 0; i < objArray.length; i++) {
                    var tagName = objArray[i].tagName.toLowerCase();
                    if (index > 0) {
                        if (tagName == tag) {
                            tmpElements.push(objArray[i]);
                        }
                    }
                    if (objArray[i] == obj) {
                        index = i;
                    }
                }
                this.elements = tmpElements;
                return this;
            }
            this.elements = tmpElements;
            return this;
        },
        /*internal function*/
        push: function (obj) {
            this.elements = [];
            this.elements.push(obj);
        },
        /***********************************************/
        /*****************events start******************/
        on: function (events, fn) {
            for (var i = 0; i < this.elements.length; i++) {
                this.bindEvent(this.elements[i], events, fn);
            }
            return this;
        },
        bind: function (events, fn, obj) {
            if (typeof obj === 'undefined') {
                for (var i = 0; i < this.elements.length; i++) {
                    this.bindEvent(this.elements[i], events, fn);
                }
            } else {
                this.bindEvent(obj, events, fn);
            }

            return this;
        },
        unbind: function (events) {
            for (var i = 0; i < this.elements.length; i++) {
                this.removeEvent(this.elements[i], events);
            }
            return this;
        },
        load: function (fn) {
            this.on('load', fn);

            return this;
        },
        click: function (fn) {
            this.on('click', fn);

            return this;
        },
        mouseover: function (fn) {
            this.on('mouseover', fn);

            return this;
        },
        mouseout: function (fn) {
            this.on('mouseout', fn);

            return this;
        },
        resize: function (fn) {
            if (fn) {
                this.bindEvent(window, 'resize', fn);
            } else {
                // 主动触发resize事件
                var ev = document.createEvent('HTMLEvents');
                ev.initEvent('resize', false, true);
                this.elements[0].dispatchEvent(ev);
            }

            return this;
        },
        hover: function (over, out) {
            this.on('mouseover', over);
            this.on('mouseout', out);

            return this;
        },
        blur: function (fn) {
            this.on('blur', fn);

            return this;
        },
        toggle: function () {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.getStyle(this.elements[i], 'display') == 'block') {
                    this.elements[i].style.display = 'none'
                } else {
                    this.elements[i].style.display = 'block'
                }
            }
            return this;
        },
        toggleClass: function (className) {
            for (var i = 0; i < this.elements.length; i++) {
                if (!this.elements[i].className.match(new RegExp('(\\s+|^)' + className + '(\\s+|$)')))
                    this.elements[i].className += ' ' + className;
                else
                    this.elements[i].className = this.elements[i].className.replace(new RegExp('(\\s+|^)' + className + '(\\s+|$)'), '');
            }

            return this;
        },
        hasClass: function (className, elem) {
            var dom = elem || this.elements[0];
            if (dom.className.match(new RegExp('(\\s+|^)' + className + '(\\s+|$)')))
                return true;

            return false;
        },
        /******************events end*******************/
        /***********************************************/
        /***********************************************/
        /*****************function start****************/
        show: function () {
            for (var i = 0; i < this.elements.length; i++) {
                this.elements[i].style.display = 'block'
            }
            return this;
        },
        hide: function () {
            for (var i = 0; i < this.elements.length; i++) {
                this.elements[i].style.display = 'none'
            }
            return this;
        },
        eq: function (num) {
            var element = this.elements[num];
            this.elements = [];
            this.elements[0] = element;
            return this;
        },
        first: function () {
            var element = this.elements[0];
            this.elements = [];
            this.elements[0] = element;
            return this;
        },
        last: function () {
            var element = this.elements[this.elements.length - 1];
            this.elements = [];
            this.elements[0] = element;
            return this;
        },
        addClass: function (className, elem) {
            if (typeof elem === 'undefined') {
                for (var i = 0; i < this.elements.length; i++) {
                    if (!this.elements[i].className.match(new RegExp('(\\s+|^)' + className + '(\\s+|$)')))
                        this.elements[i].className += ' ' + className;
                }
            } else {
                if (!elem.className.match(new RegExp('(\\s+|^)' + className + '(\\s+|$)')))
                    elem.className += ' ' + className;
            }

            return this;
        },
        removeClass: function (className, elem) {
            if (typeof elem === 'undefined') {
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].className.match(new RegExp('(\\s+|^)' + className + '(\\s+|$)')))
                        this.elements[i].className = this.elements[i].className.replace(new RegExp('(\\s+|^)' + className + '(\\s+|$)'), '');
                }
            } else {
                if (elem.className.match(new RegExp('(\\s+|^)' + className + '(\\s+|$)')))
                    elem.className = elem.className.replace(new RegExp('(\\s+|^)' + className + '(\\s+|$)'), '');
            }

            return this;
        },
        center: function () {
            for (var i = 0; i < this.elements.length; i++) {
                var obj = this.elements[i];
                var width = obj.offsetWidth;
                var height = obj.offsetHeight;
                var top = (this.getInner().height - height) / 2;
                var left = (this.getInner().width - width) / 2;
                obj.style.top = top + 'px';
                obj.style.left = left + 'px';
            }

            return this;
        },
        remove: function () {
            var self = this;
            if (this.elements.length > 0) {
                for (var i = 0; i < self.elements.length; i++) {
                    if (self.elements[i]) {
                        self.elements[i].parentNode.removeChild(self.elements[i]);
                    }
                }
            }

            return self;
        },
        serialize: function () {
            var form = this.elements[0];
            var parts = {};
            for (var i = 0; i < form.elements.length; i++) {
                var field = form.elements[i];
                switch (field.type) {
                    case undefined:
                    case 'submit':
                    case 'reset':
                    case 'file':
                    case 'button':
                        break;
                    case 'radio':
                    case 'checkbox':
                        if (typeof parts[field.name] != 'undefined') {
                            var optValue = parts[field.name];
                            parts[field.name] = (optValue + ',' + field.value);
                        } else {
                            parts[field.name] = field.value;
                        }
                        if (!field.selected)break;
                    case 'select-one':
                    case 'select-multiple':
                        for (var j = 0; j < field.options.length; j++) {
                            var option = field.options[j];
                            if (option.selected) {
                                var optValue = '';
                                if (option.hasAttribute) {
                                    optValue = (option.hasAttribute('value') ? option.value : option.text);
                                } else {
                                    optValue = (option.attributes('value').specified ? option.value : option.text);
                                }
                                parts[field.name] = optValue;
                            }
                        }
                        break;
                    default:
                        parts[field.name] = field.value;
                }
            }
            return parts;
        },
        each: function (fn) {
            var value;
            for (var i = 0; i < this.elements.length; i++) {
                var obj = this.elements[i];
                if (obj instanceof Array) {
                    for (var j = 0; j < obj.length; j++) {
                        value = fn.call(obj[j], obj[j], j);
                        if (value === false) {
                            break;
                        }
                    }
                } else {
                    value = fn.call(obj, obj, i);
                    if (value === false) {
                        break;
                    }
                }
            }
        },
        getInner: function () {
            if (typeof window.innerWidth != 'undefined') {
                return {
                    width: window.innerWidth,
                    height: window.innerHeight
                }
            } else {
                return {
                    width: document.documentElement.clientWidth,
                    height: document.documentElement.clientHeight
                }
            }
        },
        preDef: function (e) {
            var e = e || window.event;
            if (typeof e.preventDefault != 'undefined') {/*W3C*/
                e.preventDefault();
            } else {/*IE*/
                e.returnValue = false;
            }
        },
        animate: function (args) {
            if (typeof args == 'undefined') {
                seven.msg('参数错误');
                return false;
            }
            var settings = {
                speed: 50,
                interval: 30
            };
            this.initialize(settings, args);
            var self = this;
            var speed = settings.speed;
            var interval = settings.interval;

            for (var i = 0; i < this.elements.length; i++) {
                var obj = this.elements[i];
                // if there is 'opacity', will call the self.opacity method 
                if (typeof settings.opacity !== 'undefined') {
                    self.opacity(obj, args);
                    continue; // execute the next cycle
                }
                //
                var targetX = null;
                var targetY = null;
                if (typeof settings.left !== 'undefined') {
                    targetX = settings.left;
                    if (targetX < obj.offsetLeft) {
                        obj.speedX = -speed;
                    } else {
                        obj.speedX = speed;
                    }
                }
                //
                if (typeof settings.top !== 'undefined') {
                    targetY = settings.top;
                    if (targetY < obj.offsetTop) {
                        obj.speedY = -speed;
                    } else {
                        obj.speedY = speed;
                    }
                }

                clearInterval(obj.timer);
                obj.timer = setInterval(function () {
                    // left
                    if (typeof settings.left !== 'undefined') {
                        if (obj.speedX > 0) {
                            if (obj.offsetLeft >= targetX) {
                                obj.style.left = targetX + 'px';
                                obj.speedX = 0;
                            }
                        } else {
                            if (obj.offsetLeft <= targetX) {
                                obj.style.left = targetX + 'px';
                                obj.speedX = 0;
                            }
                        }
                        obj.style.left = obj.offsetLeft + obj.speedX + 'px';
                    } else {
                        obj.speedX = 0;
                    }
                    // top
                    if (typeof settings.top !== 'undefined') {
                        if (obj.speedY > 0) {
                            if (obj.offsetTop >= targetY) {
                                obj.style.top = targetY + 'px';
                                obj.speedY = 0;
                            }
                        } else {
                            if (obj.offsetTop <= targetY) {
                                obj.style.top = targetY + 'px';
                                obj.speedY = 0;
                            }
                        }
                        obj.style.top = obj.offsetTop + obj.speedY + 'px';
                    } else {
                        obj.speedY = 0;
                    }

                    if (obj.speedX === 0 && obj.speedY === 0) {
                        clearInterval(obj.timer);
                        if (typeof settings.callback == 'function') {
                            settings.callback.call(obj);
                        }
                    }
                }, interval);
            }

            return self;
        },
        drag: function (args) {
            var self = this;
            var settings = {
                dom: null // 仅点击当前dom才触发
            };
            self.initialize(settings, args);
            for (var i = 0; i < this.elements.length; i++) {
                var obj = this.elements[i];
                var _this = this.elements[i];
                if (settings.dom) {
                    obj = settings.dom;
                }
                obj.onmousedown = function (e) {
                    var e = e || window.event;
                    self.preDef(e);
                    var disX = e.clientX - _this.offsetLeft;
                    var disY = e.clientY - _this.offsetTop;
                    if (typeof _this.setCapture != 'undefined') {
                        _this.setCapture();
                    }
                    document.onmousemove = function (e) {
                        var e = e || window.event;
                        var left = e.clientX - disX;
                        var top = e.clientY - disY;
                        if (left < 0) {
                            left = 0;
                        } else if (left > self.getInner().width - _this.offsetWidth) {
                            left = self.getInner().width - _this.offsetWidth;
                        }
                        if (top < 0) {
                            top = 0;
                        } else if (top > self.getInner().height - _this.offsetHeight) {
                            top = self.getInner().height - _this.offsetHeight;
                        }
                        _this.style.left = left + 'px';
                        _this.style.top = top + 'px';
                    };
                    document.onmouseup = function () {
                        document.onmousemove = null;
                        document.onmouseup = null;
                        if (typeof _this.releaseCapture != 'undefined') {
                            _this.releaseCapture();
                        }
                    }
                }
            }

            return self;
        },
        dragEx: function () {
            var self = this;

            function startMove(obj, iSpeedX, iSpeedY) {
                obj.timer = setInterval(function () {
                    iSpeedY += 3;
                    var left = obj.offsetLeft + iSpeedX;
                    var top = obj.offsetTop + iSpeedY;
                    if (left < 0) {
                        left = 0;
                        iSpeedX = -iSpeedX;
                        iSpeedX *= 0.75;
                    } else if (left > self.getInner().width - obj.offsetWidth) {
                        left = self.getInner().width - obj.offsetWidth;
                        iSpeedX = -iSpeedX;
                        iSpeedX *= 0.75;
                    }
                    if (top < 0) {
                        top = 0;
                        iSpeedY = -iSpeedY;
                        iSpeedY *= 0.75;
                    } else if (top > self.getInner().height - obj.offsetHeight) {
                        top = self.getInner().height - obj.offsetHeight;
                        iSpeedY = -iSpeedY;
                        iSpeedY *= 0.75;
                        iSpeedX *= 0.75;
                    }
                    obj.style.left = left + 'px';
                    obj.style.top = top + 'px';
                }, 50);
            }

            for (var i = 0; i < this.elements.length; i++) {
                this.elements[i].onmousedown = function (e) {
                    self.preDef(e);
                    var _this = this;
                    clearInterval(_this.timer);
                    var e = e || window.event;
                    var disX = e.clientX - _this.offsetLeft;
                    var disY = e.clientY - _this.offsetTop;
                    var prevX = e.clientX;
                    var prevY = e.clientY;
                    var iSpeedX = 0;
                    var iSpeedY = 0;
                    if (typeof _this.setCapture != 'undefined') {
                        _this.setCapture();
                    }
                    document.onmousemove = function (e) {
                        var e = e || window.event;
                        var left = e.clientX - disX;
                        var top = e.clientY - disY;
                        iSpeedX = e.clientX - prevX;
                        iSpeedY = e.clientY - prevY;
                        prevX = e.clientX;
                        prevY = e.clientY;

                        if (left < 0) {
                            left = 0;
                        } else if (left > self.getInner().width - _this.offsetWidth) {
                            left = self.getInner().width - _this.offsetWidth;
                        }
                        if (top < 0) {
                            top = 0;
                        } else if (top > self.getInner().height - _this.offsetHeight) {
                            top = self.getInner().height - _this.offsetHeight;
                        }
                        _this.style.left = left + 'px';
                        _this.style.top = top + 'px';
                    };
                    document.onmouseup = function () {
                        document.onmousemove = null;
                        document.onmouseup = null;
                        if (typeof _this.releaseCapture != 'undefined') {
                            _this.releaseCapture();
                        }
                        startMove(_this, iSpeedX, iSpeedY);
                    }
                }
            }

            return self;
        },
        run: function (args) {
            var self = this;
            var settings = {
                speed: 6,
                interval: 50,
                left: self.getInner().width,
                top: self.getInner().height
            };
            self.initialize(settings, args);
            function getRandom(num) {
                var _num = Math.round(Math.random() * num);
                if (_num < 1)return 1;
                return _num;
            }

            function startMove(obj) {
                obj.speed = settings.speed;

                obj.iSpeedX = getRandom(settings.speed);
                obj.iSpeedY = getRandom(settings.speed);

                obj.timer = setInterval(function () {
                    var left = obj.offsetLeft + obj.iSpeedX;
                    var top = obj.offsetTop + obj.iSpeedY;
                    if (top > settings.top - obj.offsetHeight) {
                        top = settings.top - obj.offsetHeight;
                        obj.iSpeedY = -obj.iSpeedY;
                    }
                    if (left > settings.left - obj.offsetWidth) {
                        left = settings.left - obj.offsetWidth;
                        obj.iSpeedX = -obj.iSpeedX;
                    }
                    if (top < 0) {
                        top = 0;
                        obj.iSpeedY = getRandom(settings.speed);
                    }
                    if (left < 0) {
                        left = 0;
                        obj.iSpeedX = getRandom(settings.speed);
                    }
                    obj.style.left = left + 'px';
                    obj.style.top = top + 'px';
                }, settings.interval);
            }

            for (var i = 0; i < self.elements.length; i++) {
                startMove(self.elements[i]);
            }

            return self;
        },
        // seven(aSpan[j]).animateEx({ position: { top: aSpan[j].startTop }, duration: 500, easing: 'elasticOut'});
        animateEx: function (args) {
            var self = this;
            var obj = this.elements[0];
            var settings = {
                position: {}, // top: 100 ...
                duration: 500,
                easing: 'linear',
                callback: null,
                interval: 13
            };
            self.initialize(settings, args);
            clearInterval(obj.timer);
            function now() {
                return new Date().getTime();
            }

            var iCur = {};
            var startTime = now();
            for (var attr in settings.position) {
                if (attr == 'opacity') {
                    iCur[attr] = parseInt(parseFloat(this.getStyle(obj, attr)) * 100);
                } else {
                    iCur[attr] = parseInt(this.getStyle(obj, attr));
                }
            }
            //
            obj.timer = setInterval(function () {
                var changeTime = now();
                var t = settings.duration - Math.max(0, startTime - changeTime + settings.duration);
                for (var attr in settings.position) {
                    var value = self.tween[settings.easing](t, iCur[attr], settings.position[attr] - iCur[attr], settings.duration);
                    if (attr == 'opacity') {
                        obj.style.filter = 'alpha(opacity=' + value + ') ';
                        obj.style.opacity = value / 100;
                    } else {
                        obj.style[attr] = value + 'px';
                    }
                }
                if (t == settings.duration) {
                    clearInterval(obj.timer);
                    if (settings.callback) {
                        settings.callback.call(obj)
                    }
                }
            }, settings.interval);

            return self;
        },
        // 弹性文字
        wordBounce: function (args) {
            var self = this;
            var settings = {
                max: 30,
                range: 100, //
                distance: 100
            };
            self.initialize(settings, args);

            function init(container) { // 容器必须设置position
                var text = container.innerText;
                if (!text) return self;
                var newHtml = '';
                for (var i = 0; i < text.length; i++) { // 重构文档
                    newHtml += '<span>' + text[i] + '</span>';
                }
                container.innerHTML = newHtml;
                var wordNode = container.getElementsByTagName('span');
                for (var i = 0; i < wordNode.length; i++) {
                    var word = wordNode[i];
                    word.offset = seven(word).offset();
                    word.style.left = word.offset.left + 'px';
                    word.style.top = word.offset.top + 'px';
                    word.startTop = word.offset.top;
                }
            }

            for (var i = 0; i < this.elements.length; i++) {
                var obj = this.elements[i];
                init(obj);
                seven(obj).find('span').css({"position": 'absolute', "cursor": "pointer"});
                var aSpan = seven(obj).find('span').elements;
                seven(obj).find('span').each(function (item, j) {
                    (function (aSpan, nub2) {
                        var iStart = 0;
                        var oSpan = aSpan[nub2];
                        oSpan.onmouseenter = function (ev) {
                            var ev = window.event || ev;
                            iStart = ev.clientY;
                        };
                        oSpan.onmousemove = function (ev) {
                            var ev = window.event || ev;
                            var iDis = ev.clientY - iStart;
                            var iNub = iDis > 0 ? 1 : -1;
                            if (this.startTop + iDis > 0 && this.startTop + iDis < obj.offsetHeight - item.offsetHeight) {
                                for (var j = 0; j < aSpan.length; j++) {
                                    if (Math.abs(iDis) > Math.abs(nub2 - j)) {
                                        aSpan[j].style.top = aSpan[j].startTop + (Math.abs(iDis) - Math.abs(nub2 - j)) * iNub + 'px';
                                    } else {
                                        aSpan[j].style.top = aSpan[j].startTop + 'px';
                                    }
                                }
                            }
                        };
                        oSpan.onmouseleave = function (ev) {
                            for (var j = 0; j < aSpan.length; j++) {
                                seven(aSpan[j]).animateEx({
                                    position: {top: aSpan[j].startTop},
                                    duration: 500,
                                    easing: 'elasticOut'
                                });
                            }
                        }
                    })(aSpan, j)
                })

            }
            return self;
        },
        /*$(this).move({opacity:100})*/
        move: function (args, fn) {
            var self = this;
            for (var i = 0; i < this.elements.length; i++) {
                var obj = this.elements[i];
                clearInterval(obj.timer);
                obj.timer = setInterval(function () {
                    var bBtn = true;
                    for (var attr in args) {
                        var iCur = 0;
                        if (attr == 'opacity') {
                            if (Math.round(parseFloat(self.getStyle(obj, attr)) * 100) == 0) {
                                iCur = Math.round(parseFloat(self.getStyle(obj, attr)) * 100);
                            }
                            else {
                                iCur = Math.round(parseFloat(self.getStyle(obj, attr)) * 100) || 100;
                            }
                        }
                        else {
                            iCur = parseInt(self.getStyle(obj, attr)) || 0;
                        }
                        var iSpeed = (args[attr] - iCur) / 8;
                        iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
                        if (iCur != args[attr]) {
                            bBtn = false;
                        }
                        if (attr == 'opacity') {
                            obj.style.filter = 'alpha(opacity=' + (iCur + iSpeed) + ')';
                            obj.style.opacity = (iCur + iSpeed) / 100;
                        }
                        else {
                            obj.style[attr] = iCur + iSpeed + 'px';
                        }
                    }
                    if (bBtn) {
                        clearInterval(obj.timer);
                        if (fn) {
                            fn.call(obj);
                        }
                    }
                }, 30);
            }

            return self;
        },
        /*internal function*/
        motion: function (obj, args, fn) {
            var self = this;
            clearInterval(obj.timer);
            obj.timer = setInterval(function () {
                var bBtn = true;
                for (var attr in args) {
                    var iCur = 0;
                    iCur = parseInt(self.getStyle(obj, attr)) || 0;
                    var iSpeed = (args[attr] - iCur) / 8;
                    iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
                    if (iCur != args[attr]) {
                        bBtn = false;
                    }
                    obj.style[attr] = iCur + iSpeed + 'px';
                }
                if (bBtn) {
                    clearInterval(obj.timer);
                    if (fn) {
                        fn.call(obj);
                    }
                }
            }, 30);
        },
        /*internal function*/
        opacity: function (obj, args) {
            var self = this;
            var settings = {
                speed: 5,
                opacity: 100
            };
            self.initialize(settings, args);

            clearInterval(obj.timer);
            obj.timer = setInterval(function () {
                var flag = true;
                var iCur = Math.round(parseFloat(self.getStyle(obj, 'opacity')) * 100);
                var iSpeed = settings.speed;

                if (iCur > settings.opacity) {
                    iSpeed = -iSpeed;
                }
                if (iCur !== settings.opacity) {
                    flag = false;
                } else {
                    iSpeed = settings.opacity;
                }
                obj.style.filter = 'alpha(opacity=' + (iCur + iSpeed) + ')';
                obj.style.opacity = (iCur + iSpeed) / 100;
                if (flag) {
                    clearInterval(obj.timer);
                    if (settings.callback) {
                        settings.callback.call(obj);
                    }
                }
            }, 30);
        },
        rotation1: function (args) {
            var self = this;
            var settings = {
                interval: 3000,
                speed: 2
            }
            self.initialize(settings, args);

            var obj = this.elements[0];
            var oUl = obj.getElementsByTagName('ul')[0];
            var aLiUl = oUl.getElementsByTagName('li');
            var oOl = obj.getElementsByTagName('ol')[0];
            var aLiOl = oOl.getElementsByTagName('li');
            var iNow = 0;

            function toShow(index) {
                for (var y = 0; y < aLiOl.length; y++) {
                    aLiOl[y].className = '';
                    self.opacity(aLiUl[y], {opacity: 0, speed: settings.speed});
                }
                aLiOl[index].className = 'active';
                obj.style.display = 'block';
                self.opacity(aLiUl[index], {opacity: 100, speed: settings.speed});
            }

            function toRun() {
                iNow++;
                if (iNow > aLiUl.length - 1) {
                    iNow = 0;
                }
                toShow(iNow);
            }

            for (var x = 0; x < aLiOl.length; x++) {
                aLiOl[x].index = x;
                aLiOl[x].onmouseover = function () {
                    iNow = this.index;
                    toShow(this.index);
                }
            }
            obj.timer = setInterval(function () {
                toRun();
            }, settings.interval);

            obj.onmouseover = function () {
                clearInterval(this.timer);
            };
            obj.onmouseout = function () {
                this.timer = setInterval(function () {
                    toRun();
                }, settings.interval);
            };

            return self;
        },
        rotation2: function (interval) {
            if (!interval) interval = 3000;
            var self = this;

            function toRun(obj, aLiOl, aLiUl) {
                if (obj.iNow == 0) {
                    aLiUl[0].style.position = 'static';
                    obj.style.top = 0;
                    obj.iNow2 = 0;
                }
                if (obj.iNow == aLiOl.length - 1) {
                    obj.iNow = 0;
                    aLiUl[0].style.position = 'relative';
                    aLiUl[0].style.top = aLiUl.length * aLiUl[0].offsetHeight + 'px';
                } else {
                    obj.iNow++;
                }
                obj.iNow2++;
                for (var i = 0; i < aLiOl.length; i++) {
                    aLiOl[i].className = '';
                }
                aLiOl[obj.iNow].className = 'active';
                self.motion(obj, {top: -obj.iNow2 * oHeight})
            }

            var obj = this.elements[0];
            var oUl = obj.getElementsByTagName('ul')[0];
            var aLiUl = oUl.getElementsByTagName('li');
            var oOl = obj.getElementsByTagName('ol')[0];
            var aLiOl = oOl.getElementsByTagName('li');
            var oHeight = aLiUl[0].offsetHeight;
            oUl.iNow = 0;
            oUl.iNow2 = 0;

            obj.onmouseover = function () {
                clearInterval(oUl.timer1);
            };
            obj.onmouseout = function () {
                oUl.timer1 = setInterval(function () {
                    toRun(oUl, aLiOl, aLiUl);
                }, interval);
            };

            for (var x = 0; x < aLiOl.length; x++) {
                aLiOl[x].index = x;
                aLiOl[x].onmouseover = function () {
                    for (var y = 0; y < aLiOl.length; y++) {
                        aLiOl[y].className = '';
                    }
                    this.className = 'active';
                    oUl.iNow = this.index;
                    oUl.iNow2 = this.index;
                    self.motion(oUl, {top: -this.index * oHeight});
                }
            }
            oUl.timer1 = setInterval(function () {
                toRun(oUl, aLiOl, aLiUl);
            }, interval);

            return self;
        },
        rotation3: function (interval) {
            if (!interval) interval = 3000;
            var self = this;

            function toRun(obj, aLiOl, aLiUl) {
                if (obj.iNow == 0) {
                    aLiUl[0].style.position = 'static';
                    obj.style.left = 0;
                    obj.iNow2 = 0;
                }
                if (obj.iNow == aLiOl.length - 1) {
                    obj.iNow = 0;
                    aLiUl[0].style.position = 'relative';
                    aLiUl[0].style.left = aLiUl.length * oWidth + 'px';
                } else {
                    obj.iNow++;
                }
                obj.iNow2++;
                for (var i = 0; i < aLiOl.length; i++) {
                    aLiOl[i].className = '';
                }
                aLiOl[obj.iNow].className = 'active';
                self.motion(obj, {left: -obj.iNow2 * oWidth})
            }

            var obj = this.elements[0];
            var oUl = obj.getElementsByTagName('ul')[0];
            var aLiUl = oUl.getElementsByTagName('li');
            var oOl = obj.getElementsByTagName('ol')[0];
            var aLiOl = oOl.getElementsByTagName('li');
            var oWidth = aLiUl[0].offsetWidth;
            oUl.style.width = (aLiUl.length + 1) * oWidth;
            oUl.iNow = 0;
            oUl.iNow2 = 0;

            obj.onmouseover = function () {
                clearInterval(oUl.timer1);
            };
            obj.onmouseout = function () {
                oUl.timer1 = setInterval(function () {
                    toRun(oUl, aLiOl, aLiUl);
                }, interval);
            };

            for (var x = 0; x < aLiOl.length; x++) {
                aLiOl[x].index = x;
                aLiOl[x].onmouseover = function () {
                    for (var y = 0; y < aLiOl.length; y++) {
                        aLiOl[y].className = '';
                    }
                    this.className = 'active';
                    oUl.iNow = this.index;
                    oUl.iNow2 = this.index;
                    self.motion(oUl, {left: -this.index * oWidth});
                }
            }
            oUl.timer1 = setInterval(function () {
                toRun(oUl, aLiOl, aLiUl);
            }, interval);

            return self;
        },
        rotation4: function (interval) {
            if (!interval) interval = 3000;
            var self = this;

            var obj = this.elements[0];
            var oUl = obj.getElementsByTagName('ul')[0];
            var aLiUl = oUl.getElementsByTagName('li');
            var oOl = obj.getElementsByTagName('ol')[0];
            var aLiOl = oOl.getElementsByTagName('li');
            var oWidth = aLiUl[0].offsetWidth;
            obj.iNow = 0;
            obj.index = 0;
            obj.mutex = false;

            function toHide(aLiUl, oWidth, index) {
                for (var i = 0; i < aLiUl.length; i++) {
                    if (i !== index) {
                        aLiUl[i].style.left = oWidth + 'px';
                    }
                }
            }

            function toShow(index) {
                if (obj.mutex) return;
                obj.mutex = true;
                for (var y = 0; y < aLiOl.length; y++) {
                    aLiOl[y].className = '';
                    if (y !== index) {
                        aLiUl[y].style.zIndex = 0;
                    } else {
                        aLiUl[y].style.zIndex = 1;
                    }
                }
                aLiOl[index].className = 'active';
                obj.index = index;
                self.motion(aLiUl[index], {left: 0}, function () {
                    toHide(aLiUl, oWidth, index);
                    obj.mutex = false;
                });
                obj.iNow = index;
            }

            function toRun() {
                obj.timer = setInterval(function () {
                    if (obj.mutex) return;
                    obj.iNow++;
                    if (obj.iNow > aLiUl.length - 1) {
                        obj.iNow = 0;
                    }
                    toShow(obj.iNow);
                }, interval);
            }

            toRun();
            for (var z = 1; z < aLiUl.length; z++) {
                aLiUl[z].style.left = oWidth + 'px';
            }
            for (var x = 0; x < aLiOl.length; x++) {
                aLiOl[x].index = x;
                aLiOl[x].onmouseover = function () {
                    toShow(this.index);
                }
            }
            obj.onmouseover = function () {
                clearInterval(this.timer);
            };
            obj.onmouseout = function () {
                toRun();
            };

            return self;
        },
        rotation5: function (minWidth) {
            var self = this;
            var obj = this.elements[0];

            var oUl = obj.getElementsByTagName('ul')[0];
            var aLiUl = oUl.getElementsByTagName('li');

            if (!minWidth) minWidth = 30;
            var oWidth = aLiUl[0].offsetWidth;
            var allMinWidth = (aLiUl.length - 1) * minWidth;
            var num = Math.ceil(oWidth / aLiUl.length);

            for (var i = 0; i < aLiUl.length; i++) {
                aLiUl[i].style.left = num * i + 'px';
            }

            for (var i = 0; i < aLiUl.length; i++) {
                aLiUl[i].index = i;
                aLiUl[i].onmouseover = function () {
                    for (var j = 0; j < aLiUl.length; j++) {
                        if (j <= this.index) {
                            self.motion(aLiUl[j], {left: j * minWidth});
                        } else {
                            self.motion(aLiUl[j], {left: (oWidth - allMinWidth) + (j - 1) * minWidth});
                        }
                    }
                };
                aLiUl[i].onmouseout = function () {
                    for (var x = 0; x < aLiUl.length; x++) {
                        self.motion(aLiUl[x], {left: num * x});
                    }
                }
            }

            return self;
        },
        menu: function () {
            var self = this;
            document.onmousemove = function (e) {
                var e = e || window.event;
                for (var i = 0; i < self.elements.length; i++) {
                    var obj = self.elements[i];
                    var x = obj.offsetLeft + obj.offsetWidth / 2;
                    var y = obj.offsetTop + obj.offsetHeight / 2 + obj.parentNode.offsetTop;

                    var b = e.clientX - x;
                    var a = e.clientY - y;
                    var c = Math.sqrt(Math.pow(b, 2) + Math.pow(a, 2));
                    var scale = 1 - c / 300;
                    if (scale < 0.3) {
                        scale = 0.3;
                    }
                    obj.style.width = scale * 200 + 'px';
                    obj.style.height = scale * 200 + 'px';
                }
            };

            return self;
        },
        /*$('.x').round2d(100,30)*/
        round2d: function (r, interval) {
            if (!r) r = 50;
            if (!interval) interval = 30;
            var self = this;

            function createDom(obj) {
                var div = document.createElement('div');
                div.style.position = 'absolute';
                div.style.left = obj.offsetLeft;
                div.style.top = obj.offsetTop;
                div.style.width = obj.offsetWidth + 'px';
                div.style.height = obj.offsetHeight + 'px';
                div.style.borderRadius = '100%';
                div.style.backgroundColor = seven(obj).css('backgroundColor');
                document.body.appendChild(div);
                seven.fn.opacity(div, {opacity: 0}, function () {
                    div.remove();
                })
            }

            for (var i = 0; i < this.elements.length; i++) {
                var obj = this.elements[i];
                var x = obj.offsetLeft;
                var y = obj.offsetTop;
                var num = 0;
                clearInterval(obj.timer);

                var z = 0;
                obj.timer = setInterval(function () {
                    z++;
                    num++;
                    var a = Math.sin(num * Math.PI / 180) * r;
                    var b = Math.cos(num * Math.PI / 180) * r;
                    obj.style.left = x + b + 'px';
                    obj.style.top = y + a + 'px';
                    if (z > obj.offsetWidth) {
                        createDom(obj);
                        z = 0;
                    }
                }, interval);
            }

            return this;
        },
        round3d: function (r, interval) {
            if (!r) r = 50;
            if (!interval) interval = 30;
            var self = this;

            for (var i = 0; i < this.elements.length; i++) {
                var obj = this.elements[i];
                var x = obj.offsetLeft;
                var y = obj.offsetTop;
                var num = 0;
                clearInterval(obj.timer);

                obj.timer = setInterval(function () {
                    num++;
                    var a = Math.sin(num * Math.PI / 180) * r;
                    var b = Math.cos(num * Math.PI / 180) * r;
                    obj.style.left = x + b + 'px';

                    obj.style.width = a / 100 * r + 50 + 'px';
                    obj.style.height = a / 100 * r + 50 + 'px';

                }, interval);
            }

            return this;
        },
        trim: function (text) {
            return text == null ? "" : text.replace(/^\s+|\s+$/, '');
        },
        // end
        // 表单插件
        treeView: function (args) {
            var self = this;

            if (typeof args == 'undefined') args = {};
            var obj = {};
            // 是否展开节点
            obj.expand = (args.expand === true) ? true : false;
            obj.elem = this.elements[0];

            // 数据格式转换
            if (typeof args.data == 'undefined') {
                seven.ajax({
                    url: args.url,
                    type: 'get',
                    async: false,
                    success: function (data) {
                        obj.data = data;
                    }
                })
            } else {
                obj.data = args.data;
            }
            // 将list转为tree结构
            // 数据对象
            // obj.data
            function covert() {
                obj.newData = [];
                // 获取子节点, 递归
                function getItems(currentNode, level) {
                    var items = [];
                    for (var i = 0; i < obj.data.length; i++) {
                        if (obj.data[i][args.parentId] == currentNode[args.id]) {
                            obj.data[i].items = getItems(obj.data[i], level + 1);
                            obj.data[i].leafLevel = level + 1;
                            items.push(obj.data[i]);
                        }
                    }
                    return items;
                }

                // 获取顶级节点
                for (var i = 0; i < obj.data.length; i++) {
                    if (obj.data[i][args.parentId] == "" || obj.data[i][args.parentId] == null || obj.data[i][args.parentId] == 0) {
                        obj.data[i].items = getItems(obj.data[i], 1);
                        obj.data[i].leafLevel = 0;
                        obj.newData.push(obj.data[i]);
                    }
                }
            }

            // 转换数据格式
            covert();
            // obj.newData 格式 { id: 0, text: "文本", items: [] }
            // 当前元素obj.elem前插入容器对象div
            var parentNode = obj.elem.parentNode;
            var treeContainer = document.createElement('div');
            treeContainer.className = 't-tree-container';
            parentNode.insertBefore(treeContainer, obj.elem);
            obj.elem.style.display = 'none';
            // 插入节点，递归
            function appendNode(list, parent) {
                var node = document.createElement('ul');
                parent.appendChild(node);
                if (parent !== treeContainer && !obj.expand) {
                    node.style.display = 'none';
                }
                for (var i = 0; i < list.length; i++) {
                    var li = document.createElement('li');
                    var span = document.createElement('span');
                    if (!obj.expand) {
                        span.className = 't-tree-node-span t-tree-root-close';
                    } else {
                        span.className = 't-tree-node-span t-tree-root-open';
                    }
                    // 节点点击事件
                    self.bind('click', function () {
                        var className = 't-tree-root-close';
                        var tag = this.parentNode.getElementsByTagName('ul')[0];
                        if (self.hasClass(className, this)) {
                            this.className = 't-tree-node-span t-tree-root-open';
                            if (tag) tag.style.display = 'block';
                        } else {
                            this.className = 't-tree-node-span t-tree-root-close';
                            if (tag) tag.style.display = 'none';
                        }
                    }, span);

                    var a = document.createElement('a');
                    a.setAttribute('data-value', list[i][args.id]);
                    if (typeof args.select === 'function') {
                        args.select.call(a, list[i]);
                    }
                    a.className = 't-tree-node-text'; // 节点文本
                    a.data = list[i];
                    self.bind('click', function () {
                        obj.elem._data = this.data;
                        if (obj.elem.tagName.toLowerCase() === 'input') {
                            obj.elem.value = this.data[args.id];
                        }
                        var className = 't-tree-node-active';
                        seven(treeContainer).find('.t-tree-node-active').removeClass(className);
                        self.addClass(className, this);
                    }, a);


                    var span2 = document.createElement('span');
                    span2.className = 't-tree-child-node-text';
                    var span2_text = document.createElement('span');
                    span2_text.innerHTML = list[i][args.text];
                    span2_text.className = 't-tree-node-text-value';
                    span2.appendChild(span2_text);

                    // item显示小图标
                    if (args.icon === true) {
                        var span_icon = document.createElement('span');
                        span_icon.className = 't-tree-child-icon';
                        a.appendChild(span_icon);
                    }
                    a.appendChild(span2);
                    if (list[i].items && list[i].items.length > 0) {
                        li.appendChild(span);
                    } else {
                        // 没有子节点
                        span.className = 't-tree-node-span t-tree-node-placeholder';
                        li.appendChild(span);
                    }
                    li.appendChild(a);
                    if (list[i]['items'] && list[i]['items'].length > 0) {
                        appendNode(list[i]['items'], li);
                    }
                    node.appendChild(li, list[i]);
                }
            }

            // 插入节点
            appendNode(obj.newData, treeContainer);

            //
            self.get = function () {
                return obj.elem._data;
            };
            self.set = function (value) {
                if (typeof value === 'undefined') {
                    return false;
                }
                obj.elem.value = value;
                var className = 't-tree-node-active';
                seven(treeContainer).find('.t-tree-node-text').removeClass(className).each(function () {
                    if (this.getAttribute('data-value') == value) {
                        self.addClass(className, this);
                        // 设置数据
                        obj.elem._data = this.data;
                    }
                });
            };
            //
            if (typeof args.value !== 'undefined') {
                self.set(args.value);
            }

            return self;
        },
        // 下拉单选
        dropdownList: function (args) {
            var self = this;

            if (typeof args == 'undefined') args = {};
            var obj = {};
            obj.elem = this.elements[0];
            // 当前元素obj.elem前插入容器对象div
            var parentNode = obj.elem.parentNode;
            var dropdownContainer = document.createElement('div');
            dropdownContainer.className = 't-dropdownlist-container';
            parentNode.insertBefore(dropdownContainer, obj.elem);
            obj.elem.style.display = 'none';
            // 获取数据
            // 数据格式转换
            if (typeof args.data == 'undefined') {
                seven.ajax({
                    url: args.url,
                    type: 'get',
                    async: false,
                    success: function (data) {
                        obj.data = data;
                    }
                });
            } else {
                obj.data = args.data;
            }
            // 默认提示文字
            if (args.defaultText !== false) {
                var empty = [];
                var defaultValue = {};
                defaultValue[args.id] = '';
                defaultValue[args.text] = '请选择';
                empty.push(defaultValue);
                obj.data = empty.concat(obj.data);
            }
            // 显示部分
            var show_part = document.createElement('span');
            show_part.className = 't-dropdownlist-text t-form-elem';
            var span_span = document.createElement('span');
            var span_i = document.createElement('i');
            span_i.className = 't-dropdownlist-text-icon';
            //
            show_part.appendChild(span_span);
            show_part.appendChild(span_i);
            // 列表部份
            var ul = document.createElement('ul');
            ul.className = 't-dropdownlist-list';
            if (typeof args.height !== 'undefined') {
                ul.style.height = parseInt(args.height);
            }
            ul.style.overflow = 'auto';
            // 列表项
            for (var i = 0; i < obj.data.length; i++) {
                var li = document.createElement('li');
                li.className = 't-dropdownlist-item t-form-elem';
                li.data = obj.data[i];
                li.setAttribute('data-value', obj.data[i][args.id]);
                li.innerHTML = obj.data[i][args.text];
                self.bind('click', function () {
                    obj.elem.data = this.data;
                    // 设置显示值
                    self.set(this.data[args.id]);
                    // 隐藏列表
                    ul.style.display = 'none';
                    // 如果tag === input
                    if (obj.elem.tagName.toLowerCase() === 'input') {
                        obj.elem.value = this.data[args.id];
                    }
                }, li);
                ul.appendChild(li);
            }
            // 容器失去焦点
            self.bind('blur', function () {
                ul.style.display = 'none'
            }, dropdownContainer);
            // 点击事件
            self.bind('click', function () {
                if (ul.style.display == 'block') {
                    ul.style.display = 'none';
                } else {
                    ul.style.display = 'block';
                }
            }, show_part);
            //
            self.set = function (value) {
                if (value) {
                    for (var i = 0; i < obj.data.length; i++) {
                        if (obj.data[i][args.id] == value) {
                            span_span.innerHTML = obj.data[i][args.text];
                            obj.elem.data = obj.data[i];
                            return false;
                        }
                    }
                } else {
                    span_span.innerHTML = '请选择';
                }
            };
            self.get = function () {
                return obj.elem.data;
            };
            // 如果没有初始值，设置
            if (typeof args.value === 'undefined') {
                self.set();
            }

            dropdownContainer.appendChild(show_part);
            dropdownContainer.appendChild(ul);

            return self;
        }
    };

    seven.fn.init.prototype = seven.fn;

    seven.extend = seven.fn.extend = function () {
        var options, name, src, copy,
            target = arguments[0] || {},
            i = 1,
            length = arguments.length,
            deep = false;

        if (typeof target === 'boolean') {
            deep = target;
            target = arguments[1] || {};
            i = 2;
        }

        if (typeof target !== 'object' && typeof target !== 'function') {
            target = {};
        }

        if (length === i) {
            target = this;
            --i;
        }

        for (; i < length; i++) {
            if ((options = arguments[i]) != null) {
                for (name in options) {
                    src = target[name];
                    copy = options[name];

                    if (target === copy) {
                        continue;
                    }
                    // 浅拷贝
                    if (copy !== undefined) {
                        target[name] = copy;
                    }
                    // 此处需再作深拷贝情况处理...
                }
            }
        }

        return target;
    };
    // 创建AJAX对象
    var AJAXRequest = function () {
        var ajax = null;
        try {
            ajax = new ActiveXObject("microsoft.xmlhttp");
        } catch (e1) {
            try {
                ajax = new XMLHttpRequest();
            } catch (e2) {
                alert("你的浏览器不支持ajax，请更换浏览器");
            }
        }
        return ajax;
    };
    seven.extend({
        scrollTo: function (args) {
            var settings = {
                x: 0,
                y: 0
            };
            if (typeof args == 'number') {
                settings.y = args;
            } else {
                seven.initialize(settings, args);
            }
            var top = seven.scrollTop();
            if (typeof settings.speed != 'number') {
                settings.speed = Math.abs(Math.floor((settings.y - top) / 10));
            }

            if (settings.y < top)
                settings.speed = ~settings.speed;

            var timer = setInterval(function () {
                top = seven.scrollTop();
                if (top == settings.y) {
                    window.scrollTo(settings.x, settings.y);
                    clearInterval(timer);
                } else {
                    if (Math.abs(settings.y - top) <= Math.abs(settings.speed)) {
                        window.scrollTo(settings.x, settings.y);
                        clearInterval(timer);
                    } else {
                        window.scrollTo(settings.x, top + settings.speed);
                    }
                }
            }, 30)
        },
        initialize: function (args1, args2) {
            for (var attr in args2) {
                args1[attr] = args2[attr];
            }
        },
        scrollTop: function () {
            return document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
        },
        width: function () {
            return seven(window).innerWidth();
        },
        height: function () {
            return seven(window).innerHeight();
        },
        trim: function (text) {
            return text == null ? "" : text.replace(/^\s+|\s+$/, '');
        },
        now: function () {
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var hour = date.getHours();
            var minute = date.getMinutes();
            var second = date.getSeconds();

            function fmt(num) {
                if (num < 10)
                    return '0' + num;
                else return num;
            }

            return year + '-' + fmt(month) + '-' + fmt(day) + ' ' + fmt(hour) + ':' + fmt(minute) + ':' + fmt(second)
        },
        // get browser version
        browser: function () {
            return navigator.userAgent.toLowerCase();
        },
        isFunction: function (obj) {
            return typeof obj === 'function';
        },
        isArray: function (obj) {
            return typeof obj === 'array';
        },
        ajaxRequest: function (method, url, data, successFn, errorFn, async) {
            var ajax = AJAXRequest();
            if (typeof async === 'boolean') {
                ajax.open(method, url, async);
            } else {
                ajax.open(method, url);
            }

            //设置ajax请求头
            ajax.setRequestHeader("content-type", "application/json; charset=utf-8");

            //AJAX异步对象不断监听服务器响应状态0-1-2-3-4
            ajax.onreadystatechange = function () {
                //如果状态码为4
                if (ajax.readyState == 4) {
                    var result = ajax.responseText;
                    try { // 如果为json格式字符串，返回json对象
                        if (typeof JSON != "undefined") {
                            result = JSON.parse(result);
                        }
                        successFn.call(this, result);
                    } catch (e) {
                        successFn.call(this, result);
                    }
                }
            };
            var query = [], _data;
            if (typeof data == 'object' && method == 'GET') {
                for (var key in data) {
                    query[query.length] = encodeURI(key) + "=" + encodeURIComponent(data[key]);
                }
                _data = query.join('&');
            } else {
                _data = data;
            }

            //
            ajax.send(_data);
        },
        ajax: function (args) {
            this.ajaxRequest(args.type, args.url, args.data, args.success, args.error, args.async);
        },
        get: function (url, data, successFn, errorFn) {
            this.ajaxRequest('GET', url, data, successFn, errorFn);
        },
        post: function (url, data, successFn, errorFn) {
            this.ajaxRequest('POST', url, data, successFn, errorFn);
        },
        put: function (url, data, successFn, errorFn) {
            this.ajaxRequest('PUT', url, data, successFn, errorFn);
        },
        remove: function (url, data, successFn, errorFn) {
            this.ajaxRequest('DELETE', url, data, successFn, errorFn);
        },
        //
        each: function (obj, fn) {
            var value;
            if (obj instanceof Array) {
                for (var i = 0; i < obj.length; i++) {
                    value = fn.call(obj[i], obj[i], i);
                    // 回调函数中用this不能获取到值，待解决
                    if (value === false) {
                        break;
                    }
                }
            }
        },
        indexOf: function (array, obj) {
            var index;
            for (var i = 0; i < array.length; i++) {
                if (array[i] === obj) {
                    index = i;
                    break;
                }
            }
            return index;
        },
        alert: function (args) {
            if (typeof args == 'string') {
                var msg = args;
                args = {};
                args.msg = msg;
            }

            var settings = {
                msg: "",
                title: "信息",
                shadow: true,
                background: "normal"
            };

            seven.initialize(settings, args);

            if (settings.shadow === true) seven.lock();

            var obj = document.createElement('div');
            if (settings.background !== "normal") {
                obj.style.borderColor = settings.background;
                obj.style.background = settings.background;
            }
            obj.className = 't-message-container';
            var _title = document.createElement('div');
            _title.innerHTML = settings.title;
            _title.className = 't-message-title';
            var _content = document.createElement('div');
            _content.className = 't-message-content';
            _content.innerHTML = settings.msg;
            var _toolbar = document.createElement('div');
            _toolbar.className = 't-message-toolbar';
            var _confirm = document.createElement('span');
            _confirm.className = 't-message-ok';
            _confirm.innerHTML = '确定';
            _confirm.onclick = function () {
                if (settings.shadow === true) seven.unlock();
                seven(obj).remove();
            };
            //
            _toolbar.appendChild(_confirm);
            //
            obj.appendChild(_title);
            obj.appendChild(_content);
            obj.appendChild(_toolbar);
            //
            document.body.appendChild(obj);
            seven(obj).center();
            seven(window).resize(function () {
                seven(obj).center();
            });
        },
        confirm: function (args) {
            if (typeof args == 'string') {
                var msg = args;
                args = {};
                args.msg = msg;
            }
            var settings = {
                msg: "",
                title: "信息",
                shadow: true,
                background: "normal"
            };
            seven.initialize(settings, args);

            if (settings.shadow === true) seven.lock();

            var obj = document.createElement('div');
            if (settings.background !== "normal") {
                obj.style.borderColor = settings.background;
                obj.style.background = settings.background;
            }
            obj.className = 't-confirm-container';
            var _title = document.createElement('div');
            _title.innerHTML = settings.title;
            _title.className = 't-confirm-title';
            var _content = document.createElement('div');
            _content.className = 't-confirm-content';
            _content.innerHTML = settings.msg;
            var _toolbar = document.createElement('div');
            _toolbar.className = 't-confirm-toolbar';
            var _confirm = document.createElement('span');
            _confirm.className = 't-confirm-ok';
            _confirm.innerHTML = '确定';
            _confirm.onclick = function () {
                if (typeof settings.confirm == 'function') {
                    settings.confirm.call(obj);
                }
                if (settings.shadow === true) seven.unlock();
                seven(obj).remove();
            };
            var _cancel = document.createElement('span');
            _cancel.className = 't-confirm-cancel';
            _cancel.innerHTML = '取消';
            _cancel.onclick = function () {
                if (typeof settings.cancel == 'function') {
                    settings.cancel.call(obj);
                }
                seven.unlock();
                seven(obj).remove();
            };
            obj.close = function () {
                seven.unlock();
                seven(obj).remove();
            };
            //
            _toolbar.appendChild(_confirm);
            _toolbar.appendChild(_cancel);
            //
            obj.appendChild(_title);
            obj.appendChild(_content);
            obj.appendChild(_toolbar);
            //
            document.body.appendChild(obj);

            seven(obj).center();
            seven(window).resize(function () {
                seven(obj).center();
            });
        },
        lock: function (args) {
            var settings = {
                loading: false
            };
            seven.initialize(settings, args);

            var obj = document.createElement('div');
            obj.className = 't-lock-screen';
            obj.style.width = seven.width() + 'px';
            obj.style.height = seven.height() + 'px';

            if (settings.loading) {
                var loading = document.createElement('div');
                loading.className = 't-loading-screen';
                obj.appendChild(loading);
            }

            document.body.appendChild(obj);
            seven(window).resize(function () {
                obj.style.width = seven.width() + 'px';
                obj.style.height = seven.height() + 'px';
            });
        },
        unlock: function () {
            seven('.t-lock-screen').remove();
        },
        msg: function (args) {
            if (typeof args == 'string') {
                var msg = args;
                args = {};
                args.msg = msg;
            }
            var settings = {
                msg: "",
                fontSize: '13px',
                background: "orange",
                shadow: false
            };
            seven.initialize(settings, args);

            if (typeof settings.second != 'undefined') {
                settings.second = parseInt(settings.second);
            }

            var obj = document.createElement('div');
            obj.className = 't-msg-container';
            obj.innerHTML = settings.msg;
            if (typeof settings.background != 'undefined') {
                obj.style.background = settings.background;
                obj.style.borderColor = settings.background;
            }
            obj.style.fontSize = settings.fontSize;

            obj.onclick = function () {
                if (settings.shadow === true) seven.unlock();
                if (typeof settings.callback == 'function') {
                    settings.callback.call(obj);
                }
                seven(obj).remove();
            };
            document.body.appendChild(obj);

            if (settings.shadow === true) seven.lock();
            seven(obj).center();
            seven(window).resize(function () {
                seven(obj).center();
            });
            if (typeof settings.top != 'undefined') {
                obj.style.top = parseInt(settings.top) + 'px';
            }

            if (typeof settings.second == 'number') {
                obj.onclick = null;
                var timer = setInterval(function () {
                    if (settings.second < 1) {
                        clearInterval(timer);
                        if (settings.shadow === true) seven.unlock();
                        if (typeof settings.callback == 'function') {
                            settings.callback.call(obj);
                        }
                        seven(obj).remove();
                    }
                    settings.second--;

                }, 1000);
            }
        },
        open: function (args) {
            if (typeof args == 'string') {
                var url = args;
                args = {};
                args.url = url;
            }
            var settings = {
                title: "新窗口",
                shadow: true,
                drag: false,
                width: 500,
                height: 300
            };
            seven.initialize(settings, args);
            if (typeof settings.url == 'undefined') {
                seven.msg('参数错误~！');
                return false;
            }
            settings.width = parseInt(settings.width) + 'px';
            settings.height = parseInt(settings.height) + 'px';

            var obj = document.createElement('div');
            if (settings.drag === true) {
                seven(obj).drag();
            }
            obj.className = 't-dialog-container';
            obj.style.width = settings.width;
            obj.style.height = settings.height;
            var titleDiv = document.createElement('div');
            titleDiv.className = 't-dialog-title';
            titleDiv.innerHTML = settings.title;
            var closeSpan = document.createElement('span');
            closeSpan.className = 't-dialog-close';
            closeSpan.onclick = closeSpan.close = function () {
                if (settings.shadow === true) seven.unlock();
                seven(obj).remove();
            };
            titleDiv.appendChild(closeSpan);
            obj.close = closeSpan.close;

            var iframe = document.createElement('iframe');
            iframe.src = settings.url;
            iframe.frameBorder = 'no';
            iframe.border = '0';
            iframe.scrolling = 'auto';
            iframe.width = settings.width;
            iframe.height = (parseInt(settings.height) - 31) + 'px';

            obj.appendChild(titleDiv);
            obj.appendChild(iframe);

            document.body.appendChild(obj);
            if (settings.shadow === true) seven.lock();
            seven(obj).center();
            seven(window).resize(function () {
                seven(obj).center();
            });
            // 回调函数
            if (typeof settings.callback === 'function') {
                settings.callback.call(obj);
            }

            return obj;
        },
        login: function (args) {
            if (typeof args == 'undefined') {
                args = {};
            }
            var settings = {
                title: "用户登录",
                shadow: true,
                userName: '用户名',
                passWord: '密码',
                showForget: true,
                showRemember: true
            };
            seven.initialize(settings, args);

            var obj = document.createElement('div');
            obj.className = 'login-container';
            //create userName
            var user = document.createElement('div');
            user.className = 'login-container-item';
            var userLabel = document.createElement('label');
            userLabel.innerHTML = '用户名';
            var userInputWrap = document.createElement('p');
            var discard = document.createElement('input');
            discard.type = "password";
            discard.style.display = 'none';
            obj.appendChild(discard);
            var userInput = document.createElement('input');
            userInput.type = 'text';
            userInput.name = 'userName';
            userInput.setAttribute('autocomplete', "off");
            userInput.setAttribute('placeholder', settings.userName);

            userInputWrap.appendChild(userInput);
            user.appendChild(userLabel);
            user.appendChild(userInputWrap);
            //create password
            var pwd = document.createElement('div');
            pwd.className = 'login-container-item';
            var pwdLabel = document.createElement('label');
            pwdLabel.innerHTML = '密码';
            var pwdInputWrap = document.createElement('p');
            var pwdInput = document.createElement('input');
            pwdInput.type = 'password';
            pwdInput.name = 'passWord';
            pwdInput.setAttribute('autocomplete', "off");
            pwdInput.setAttribute('placeholder', settings.passWord);
            pwdInputWrap.appendChild(pwdInput);
            pwd.appendChild(pwdLabel);
            pwd.appendChild(pwdInputWrap);
            //create toolbar
            var toolbar = document.createElement('div');
            toolbar.className = 'login-toolbar';
            var forget_p = document.createElement('p');
            forget_p.className = 'forgot-password';
            var error = document.createElement('span');
            error.className = 'errorMsg';
            var forget_link = document.createElement('a');
            forget_link.href = 'javascript:;';
            forget_link.innerHTML = '忘记密码?';
            if (settings.showForget == false) {
                forget_link.style.visibility = 'hidden';
            }
            forget_link.onclick = function () {
                if (typeof settings.forget == 'function') {
                    settings.forget.call(obj);
                }
            };
            forget_p.appendChild(error);
            forget_p.appendChild(forget_link);
            //
            var buttons = document.createElement('p');
            buttons.className = 'login-buttons';
            var checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.id = 'rememberPassword';
            var buttons_label = document.createElement('label');
            buttons_label.htmlFor = 'rememberPassword';
            buttons_label.innerHTML = '记住密码';
            if (settings.showRemember == false) {
                checkbox.style.visibility = 'hidden';
                buttons_label.style.visibility = 'hidden';
            }
            var btn = document.createElement('span');
            btn.innerHTML = '登录';
            userInput.onchange = function () {
                if (userInput.value.replace(/\s+/, '').length > 0) {
                    userInput.style.borderColor = '#ccc';
                    error.innerHTML = '';
                }
            };
            pwdInput.onchange = function () {
                if (pwdInput.value.replace(/\s+/, '').length > 0) {
                    pwdInput.style.borderColor = '#ccc';
                    error.innerHTML = '';
                }
            };
            btn.onclick = function () {
                if (userInput.value.replace(/\s+/, '').length == 0) {
                    userInput.style.borderColor = '#d00';
                    userInput.focus();
                    return false;
                }
                if (pwdInput.value.replace(/\s+/, '').length == 0) {
                    pwdInput.style.borderColor = '#d00';
                    pwdInput.focus();
                    return false;
                }
                if (typeof settings.submit == 'function') {
                    var result = {
                        userName: userInput.value,
                        passWord: pwdInput.value,
                        remember: checkbox.checked
                    };
                    settings.submit.call(obj, result);
                }
            };
            buttons.appendChild(checkbox);
            buttons.appendChild(buttons_label);
            buttons.appendChild(btn);
            //
            toolbar.appendChild(forget_p);
            toolbar.appendChild(buttons);
            //
            obj.appendChild(user);
            obj.appendChild(pwd);
            obj.appendChild(toolbar);
            obj.close = function () {
                if (settings.shadow === true) seven.unlock();
                seven(obj).remove();
            };
            obj.msg = function (msg) {
                error.innerHTML = msg;
            };
            document.body.appendChild(obj);
            if (settings.shadow === true) seven.lock();
            seven(obj).center();
            seven(window).resize(function () {
                seven(obj).center();
            });
        },
        upload: function (args) {
            if (typeof args == 'undefined') {
                seven.msg('参数错误');
                return false;
            }
            var settings = {
                url: '',
                loading: false,
                success: null,
                error: null,
                file: 'file',
                maxSize: 2048000,
                accept: null
            };
            seven.initialize(settings, args);
            // 防id名称冲突，先remove
            function clearUploadDom() {
                if (seven('#' + settings.file).length > 0)
                    seven('#' + settings.file).remove();
                if (seven('#uploadIframe').length > 0)
                    seven('#uploadIframe').remove();
                if (seven('#turnForm').length > 0)
                    seven('#turnForm').remove();
            }

            clearUploadDom();

            var iFrame = null;
            try { // for I.E.
                iFrame = document.createElement('<iframe name="uploadIframe">');
            } catch (ex) { //for other browsers, an exception will be thrown
                iFrame = document.createElement('iframe');
            }
            iFrame.id = 'uploadIframe';
            iFrame.name = 'uploadIframe';
            iFrame.style.display = 'none';
            iFrame.onload = function () {
                var win = iFrame.contentWindow;
                if (!win.document.body) {
                    return false;
                }
                var response = win.document.body.innerHTML;
                if (response.replace(/\s+/).length == 0) {
                    return false;
                } else {
                    seven.unlock();
                    settings.success.call(this, response);
                    clearUploadDom();
                }
            };
            document.body.appendChild(iFrame);
            //create form
            var turnForm = document.createElement("form");
            document.body.appendChild(turnForm);
            turnForm.method = 'post';
            turnForm.id = 'turnForm';
            turnForm.action = settings.url;
            turnForm.enctype = "multipart/form-data";
            turnForm.target = 'uploadIframe';
            turnForm.style.display = "none";
            //create file controlers
            var fileElement = document.createElement("input");
            fileElement.id = settings.file;
            fileElement.setAttribute("name", settings.file);
            fileElement.setAttribute("type", "file");
            fileElement.style.display = "none";
            fileElement.onchange = function (e) {
                var e = e || window.event;
                var files = e.target.files;
                if (files) { // files属性不兼容低版本浏览器
                    if (files[0].size > settings.maxSize) {
                        settings.error.call(this, "the file is too large");
                        return false;
                    }
                }
                if (settings.accept) { // 验证文件类型
                    var fileName = e.target.value;
                    var index = fileName.lastIndexOf(".");
                    var extensionName = fileName.substring(index + 1);
                    if (!settings.accept.match(extensionName)) {
                        settings.error.call(this, "invalid file type");
                        return false;
                    }
                }
                // 

                if (settings.loading) seven.lock({loading: true});
                turnForm.submit();
            };

            turnForm.appendChild(fileElement);
            fileElement.click();
        },
        isNull: function (string) {
            if (!string) return true;
            if (string.replace(/\s+/, '').length == 0) return true;
            return false;
        },
        random: function (n, m) {
            var num = Math.random() * (m - n) + n;
            return parseInt(num);
        },
        cookie: {
            add: function (key, value, expiredays) {
                expiredays = expiredays ? expiredays : 1000;
                var exdate = new Date();
                exdate.setDate(exdate.getDate() + expiredays);
                document.cookie = key + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
            },
            get: function (key) {
                if (document.cookie.length > 0) {
                    var c_start = document.cookie.indexOf(key + "=");
                    if (c_start != -1) {
                        c_start = c_start + key.length + 1;
                        var c_end = document.cookie.indexOf(";", c_start);
                        if (c_end == -1) c_end = document.cookie.length;
                        return unescape(document.cookie.substring(c_start, c_end));
                    }
                }
                return null;
            }
        }

    });


    window.s = seven;
})(window);
