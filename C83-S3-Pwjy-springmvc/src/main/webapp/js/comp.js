	//使用vue的自定义组件
	//Vue.compoment('自定义组件名',{自定义组件json对象});
	Vue.component( 'page-head' , {
	// 反引号  Esc 下面的那个按键,  反引号定义的字符串, 内部可以换行, 不需要拼接字符串
	
	// 这里没错, 是外面的 detail.html 页面代码的问题,已经修改
	template : `
		<div  class="container header">
		
		
		<div class="span5">
			<div class="logo">
				<a href="index.html">
					<img src="image/r___________renleipic_01/logo.png" alt="依依不舍"/>
				</a>
			</div>
		</div>
		<div class="span9">
			<div class="headerAd">
				<img src="image/header.jpg" width="320" height="50" alt="正品保障" title="正品保障"/>
			</div>	
		</div>
		<div class="span10 last">
			<div class="topNav clearfix">
				<ul>
					
						<li id="headerLogin" class="headerLogin" style="display: list-item;">
							<span v-if="loginUser!='' ">
							{{loginUser}}
							<a id="leave" href="javascript.void(0)" @click="tui()">[退出]</a>
							</span>
							<span v-else><a href="login.html">登录</a></span>|
						</li>
						<li id="headerRegister" class="headerRegister" style="display: list-item;">
							<a href="reg.html">注册</a>|
						</li>
						<li id="headerOrder" class="headerOrder" style="display: list-item;">
							<span v-if="loginUser!='' "><a href="olist.html">我的订单|</a></span>
						</li>
						<li>
							<a href="person.html">个人中心</a>|
						</li>
						<li>
							<a href="javascript:void(0)">购物指南</a>|
						</li>
						<li>
							<a href="javascript:void(0)">关于我们</a>
						</li>
				</ul>
			</div>
			<div class="cart">
				<a  href="cart.html">购物车</a>
			</div>
				<div class="phone">
					客服热线:
					<strong>96008/53277764</strong>
				</div>
		</div>
		
	<div class="span24">
			<ul class="mainNav">
						<li>
							<a href="index.html">首页</a>
							|
						</li>
						
						<li  v-for="m in Menu">
							<a :href="'clist.html#'+m.id" @click="tiao(m.id)">{{m.cname}}</a>|
						</li>				
			</ul>
		</div>
		<slot></slot>
	</div>	`,
	data: function () {
		return {
	      loginUser:"",//存储登录的用户名
	      Menu:[],//保存菜单栏
	   	}
	 },
	created:function(){
		//登录成功后加载用户名
		this.load();
		//显示菜单导航栏
		axios.get("category.do?op=queryMenu").then(res=>{	
			//获取菜单栏
			this.Menu=res.data;
		});
	},
	methods:{
		//加载登录的用户名
		load(){
			//获取登录的用户名
			axios.get("user.do?op=getUname").then( (res)=> {
				if(res.data){
					//获取登录的用户名
					this.loginUser=res.data;
					//console.log(res.data);
				}
			});
		},
		//退出
		tui(){
			//清空登录的用户名
			this.loginUser="";
			axios.get("user.do?op=delLogin").then( (res)=> {
				alert(res.data);
				//location.href="login.html";//页面跳转
				location.reload();//页面刷新
			});
			
		},
		//用于页面刷新
		tiao(id){
			//location.href='clist.html#'+id;
			location.reload();//页面刷新		
		}
	}
} );

	
Vue.component('page-foot',{
	template :`
	<div class="container footer">
		<div class="span24">
			<div class="footerAd">
						<img src="image/footer.jpg" width="950" height="52" alt="我们的优势" title="我们的优势">
	</div>	</div>
		<div class="span24">
			<ul class="bottomNav">
						<li>
							<a>关于我们</a>
							|
						</li>
						<li>
							<a>联系我们</a>
							|
						</li>
						<li>
							<a>招贤纳士</a>
							|
						</li>
						<li>
							<a>法律声明</a>
							|
						</li>
						<li>
							<a>友情链接</a>
							|
						</li>
						<li>
							<a target="_blank">支付方式</a>
							|
						</li>
						<li>
							<a target="_blank">配送方式</a>
							|
						</li>
						<li>
							<a>服务声明</a>
							|
						</li>
						<li>
							<a>广告声明</a>
							
						</li>
			</ul>
		</div>
		<div class="span24">
			<div class="copyright">Copyright © 2005-2013 大麦商城 版权所有</div>
		</div>
	</div>
	`
});