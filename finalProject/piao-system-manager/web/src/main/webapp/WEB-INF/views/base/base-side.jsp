<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- 顶部导航栏部分 -->
<header class="main-header">
    <!-- Logo -->
    <a href="index2.html" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>年票</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>年票系统</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </a>

        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- User Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="/static/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                        <span class="hidden-xs">${sessionScope.curr_user.name}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <img src="/static/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                            <p>
                                <small>

                                </small>
                            </p>
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="/profile" class="btn btn-default btn-flat">个人设置</a>
                            </div>
                            <div class="pull-right">
                                <a href="/logout" class="btn btn-default btn-flat">安全退出</a>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>

<!-- =============================================== -->

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- 搜索表单，不需要删除即可 -->
        <!--<form action="#" method="get" class="sidebar-form">
          <div class="input-group">
            <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                  <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                  </button>
                </span>
          </div>
        </form>-->
        <!-- /.search form -->
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header">系统功能</li>
            <%--首页--%>
            <li class="${param.active == 'home' ? 'active' : ''}"><a href="/home"><i class="fa fa-home"></i>
                <span>首页</span></a></li>
            <shiro:hasRole name="基本信息">
            <li class="treeview ${fn:startsWith(param.active, "customer_") ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-address-book-o"></i> <span>基本信息管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.active == 'customer_my' ? 'active' : ''}"><a href="/customer/my"><i
                            class="fa fa-circle-o"></i> 我的客户</a></li>
                    <li><a href="/customer/public"><i class="fa fa-circle-o"></i> 公海客户</a></li>
                </ul>
            </li>
            </shiro:hasRole>
            <shiro:hasRole name="综合办公">
            <li class="treeview ${fn:startsWith(param.active, "sales_") ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-money"></i> <span>综合办公</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.active == 'sales_my' ? 'active' : ''}"><a href="/sales/my"><i class="fa fa-circle-o"></i> 我的机会</a></li>
                    <li class="${param.active == 'sales_public' ? 'active' : ''}"><a href="/sales/public"><i class="fa fa-circle-o"></i> 公共记录</a></li>
                </ul>
            </li>
            </shiro:hasRole>
            <shiro:hasRole name="系统管理">
            <li class="${param.active == 'task' ? 'active' : ''}">
                <a href="/task">
                    <i class="fa fa-calendar"></i> <span>系统管理</span>
                </a>
            </li>
            </shiro:hasRole>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->