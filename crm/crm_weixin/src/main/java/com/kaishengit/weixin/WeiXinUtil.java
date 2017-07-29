package com.kaishengit.weixin;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.kaishengit.weixin.exception.WeiXinException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class WeiXinUtil {

    private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}";
    private static final String CREATE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token={0}";
    private static final String DELETE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token={0}&id={1}";
    private static final String CREATE_ACCOUNT_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token={0}";
    private static final String DELETE_ACCOUNT_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token={0}&userid={1}";
    private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={0}";

    public static final String TOKEN_TYPE_ADDRESS_BOOK = "addressBook";
    public static final String TOKEN_TYPE_NORMAL = "";

    private static Map<Integer,String> errorCodeMap = new HashMap<>();

    static {
        errorCodeMap.put(-1, "系统繁忙");
        errorCodeMap.put(40001 ,"获取access_token时Secret错误，或者access_token无效");
        errorCodeMap.put(40002 ,"不合法的凭证类型");
        errorCodeMap.put(40003 ,"不合法的UserID");
        errorCodeMap.put(40004 ,"不合法的媒体文件类型");
        errorCodeMap.put(40005 ,"不合法的文件类型");
        errorCodeMap.put(40006 ,"不合法的文件大小");
        errorCodeMap.put(40007 ,"不合法的媒体文件id");
        errorCodeMap.put(40008 ,"不合法的消息类型");
        errorCodeMap.put(40013 ,"不合法的corpid");
        errorCodeMap.put(40014 ,"不合法的access_token");
        errorCodeMap.put(40015 ,"不合法的菜单类型");
        errorCodeMap.put(40016 ,"不合法的按钮个数");
        errorCodeMap.put(40017 ,"不合法的按钮类型");
        errorCodeMap.put(40018 ,"不合法的按钮名字长度");
        errorCodeMap.put(40019 ,"不合法的按钮KEY长度");
        errorCodeMap.put(40020 ,"不合法的按钮URL长度");
        errorCodeMap.put(40021 ,"不合法的菜单版本号");
        errorCodeMap.put(40022 ,"不合法的子菜单级数");
        errorCodeMap.put(40023 ,"不合法的子菜单按钮个数");
        errorCodeMap.put(40024 ,"不合法的子菜单按钮类型");
        errorCodeMap.put(40025 ,"不合法的子菜单按钮名字长度");
        errorCodeMap.put(40026 ,"不合法的子菜单按钮KEY长度");
        errorCodeMap.put(40027 ,"不合法的子菜单按钮URL长度");
        errorCodeMap.put(40028 ,"不合法的自定义菜单使用成员");
        errorCodeMap.put(40029 ,"不合法的oauth_code");
        errorCodeMap.put(40031 ,"不合法的UserID列表");
        errorCodeMap.put(40032 ,"不合法的UserID列表长度");
        errorCodeMap.put(40033 ,"不合法的请求字符，不能包含\\uxxxx格式的字符");
        errorCodeMap.put(40035 ,"不合法的参数");
        errorCodeMap.put(40038 ,"不合法的请求格式");
        errorCodeMap.put(40039 ,"不合法的URL长度");
        errorCodeMap.put(40040 ,"不合法的插件token");
        errorCodeMap.put(40041 ,"不合法的插件id");
        errorCodeMap.put(40042 ,"不合法的插件会话");
        errorCodeMap.put(40048 ,"url中包含不合法domain");
        errorCodeMap.put(40054 ,"不合法的子菜单url域名");
        errorCodeMap.put(40055 ,"不合法的按钮url域名");
        errorCodeMap.put(40056 ,"不合法的agentid");
        errorCodeMap.put(40057 ,"不合法的callbackurl或者callbackurl验证失败");
        errorCodeMap.put(40058 ,"不合法的红包参数");
        errorCodeMap.put(40059 ,"不合法的上报地理位置标志位");
        errorCodeMap.put(40060 ,"设置上报地理位置标志位时没有设置callbackurl");
        errorCodeMap.put(40061 ,"设置应用头像失败");
        errorCodeMap.put(40062 ,"不合法的应用模式");
        errorCodeMap.put(40063 ,"参数为空");
        errorCodeMap.put(40064 ,"管理组名字已存在");
        errorCodeMap.put(40065 ,"不合法的管理组名字长度");
        errorCodeMap.put(40066 ,"不合法的部门列表");
        errorCodeMap.put(40067 ,"标题长度不合法");
        errorCodeMap.put(40068 ,"不合法的标签ID");
        errorCodeMap.put(40069 ,"不合法的标签ID列表");
        errorCodeMap.put(40070 ,"列表中所有标签（成员）ID都不合法");
        errorCodeMap.put(40071 ,"不合法的标签名字，标签名字已经存在");
        errorCodeMap.put(40072 ,"不合法的标签名字长度");
        errorCodeMap.put(40073 ,"不合法的openid");
        errorCodeMap.put(40074 ,"news消息不支持指定为高保密消息");
        errorCodeMap.put(40077 ,"不合法的预授权码");
        errorCodeMap.put(40078 ,"不合法的临时授权码");
        errorCodeMap.put(40079 ,"不合法的授权信息");
        errorCodeMap.put(40080 ,"不合法的suitesecret");
        errorCodeMap.put(40082 ,"不合法的suitetoken");
        errorCodeMap.put(40083 ,"不合法的suiteid");
        errorCodeMap.put(40084 ,"不合法的永久授权码");
        errorCodeMap.put(40085 ,"不合法的suiteticket");
        errorCodeMap.put(40086 ,"不合法的第三方应用appid");
        errorCodeMap.put(40092 ,"导入文件存在不合法的内容");
        errorCodeMap.put(40093 ,"不合法的跳转target");
        errorCodeMap.put(40094 ,"不合法的URL");
        errorCodeMap.put(40095 ,"修改失败，并发冲突");
        errorCodeMap.put(40155 ,"请勿添加其他公众号的主页链接");
        errorCodeMap.put(41001 ,"缺少access_token参数");
        errorCodeMap.put(41002 ,"缺少corpid参数");
        errorCodeMap.put(41003 ,"缺少refresh_token参数");
        errorCodeMap.put(41004 ,"缺少secret参数");
        errorCodeMap.put(41005 ,"缺少多媒体文件数据");
        errorCodeMap.put(41006 ,"缺少media_id参数");
        errorCodeMap.put(41007 ,"缺少子菜单数据");
        errorCodeMap.put(41008 ,"缺少oauth code");
        errorCodeMap.put(41009 ,"缺少UserID");
        errorCodeMap.put(41010 ,"缺少url");
        errorCodeMap.put(41011 ,"缺少agentid");
        errorCodeMap.put(41012 ,"缺少应用头像mediaid");
        errorCodeMap.put(41013 ,"缺少应用名字");
        errorCodeMap.put(41014 ,"缺少应用描述");
        errorCodeMap.put(41015 ,"缺少Content");
        errorCodeMap.put(41016 ,"缺少标题");
        errorCodeMap.put(41017 ,"缺少标签ID");
        errorCodeMap.put(41018 ,"缺少标签名字");
        errorCodeMap.put(41021 ,"缺少suiteid");
        errorCodeMap.put(41022 ,"缺少suitetoken");
        errorCodeMap.put(41023 ,"缺少suiteticket");
        errorCodeMap.put(41024 ,"缺少suitesecret");
        errorCodeMap.put(41025 ,"缺少永久授权码");
        errorCodeMap.put(41034 ,"缺少login_ticket");
        errorCodeMap.put(41035 ,"缺少跳转target");
        errorCodeMap.put(42001 ,"access_token过期");
        errorCodeMap.put(42002 ,"refresh_token过期");
        errorCodeMap.put(42003 ,"oauth_code过期");
        errorCodeMap.put(42004 ,"插件token过期");
        errorCodeMap.put(42007 ,"预授权码失效");
        errorCodeMap.put(42008 ,"临时授权码失效");
        errorCodeMap.put(42009 ,"suitetoken失效");
        errorCodeMap.put(43001 ,"需要GET请求");
        errorCodeMap.put(43002 ,"需要POST请求");
        errorCodeMap.put(43003 ,"需要HTTPS");
        errorCodeMap.put(43004 ,"需要成员已关注");
        errorCodeMap.put(43005 ,"需要好友关系");
        errorCodeMap.put(43006 ,"需要订阅");
        errorCodeMap.put(43007 ,"需要授权");
        errorCodeMap.put(43008 ,"需要支付授权");
        errorCodeMap.put(43010 ,"需要处于接收消息模式");
        errorCodeMap.put(43011 ,"需要企业授权");
        errorCodeMap.put(43013 ,"应用对成员不可见");
        errorCodeMap.put(44001 ,"多媒体文件为空");
        errorCodeMap.put(44002 ,"POST的数据包为空");
        errorCodeMap.put(44003 ,"图文消息内容为空");
        errorCodeMap.put(44004 ,"文本消息内容为空");
        errorCodeMap.put(45001 ,"多媒体文件大小超过限制");
        errorCodeMap.put(45002 ,"消息内容大小超过限制");
        errorCodeMap.put(45003 ,"标题大小超过限制");
        errorCodeMap.put(45004 ,"描述大小超过限制");
        errorCodeMap.put(45005 ,"链接长度超过限制");
        errorCodeMap.put(45006 ,"图片链接长度超过限制");
        errorCodeMap.put(45007 ,"语音播放时间超过限制");
        errorCodeMap.put(45008 ,"图文消息的文章数量不能超过8条");
        errorCodeMap.put(45009 ,"接口调用超过限制");
        errorCodeMap.put(45010 ,"创建菜单个数超过限制");
        errorCodeMap.put(45015 ,"回复时间超过限制");
        errorCodeMap.put(45016 ,"系统分组，不允许修改");
        errorCodeMap.put(45017 ,"分组名字过长");
        errorCodeMap.put(45018 ,"分组数量超过上限");
        errorCodeMap.put(45022 ,"应用名字长度不合法，合法长度为2-16个字");
        errorCodeMap.put(45024 ,"帐号数量超过上限");
        errorCodeMap.put(45025 ,"同一个成员每周只能邀请一次");
        errorCodeMap.put(45026 ,"触发删除用户数的保护");
        errorCodeMap.put(45027 ,"mpnews每天只能发送100次");
        errorCodeMap.put(45028 ,"素材数量超过上限");
        errorCodeMap.put(45029 ,"media_id对该应用不可见");
        errorCodeMap.put(45032 ,"作者名字长度超过限制");
        errorCodeMap.put(46001 ,"不存在媒体数据");
        errorCodeMap.put(46002 ,"不存在的菜单版本");
        errorCodeMap.put(46003 ,"不存在的菜单数据");
        errorCodeMap.put(46004 ,"不存在的成员");
        errorCodeMap.put(47001 ,"解析JSON/XML内容错误");
        errorCodeMap.put(48001 ,"Api未授权");
        errorCodeMap.put(48002 ,"Api禁用(一般是管理组类型与Api不匹配，例如普通管理组调用会话服务的Api)");
        errorCodeMap.put(48003 ,"suitetoken无效");
        errorCodeMap.put(48004 ,"授权关系无效");
        errorCodeMap.put(48005 ,"Api已废弃");
        errorCodeMap.put(50001 ,"redirect_uri未授权");
        errorCodeMap.put(50002 ,"成员不在权限范围");
        errorCodeMap.put(50003 ,"应用已停用");
        errorCodeMap.put(50004 ,"成员状态不正确，需要成员为企业验证中状态");
        errorCodeMap.put(50005 ,"企业已禁用");
        errorCodeMap.put(60001 ,"部门长度不符合限制");
        errorCodeMap.put(60002 ,"部门层级深度超过限制");
        errorCodeMap.put(60003 ,"部门不存在");
        errorCodeMap.put(60004 ,"父部门不存在");
        errorCodeMap.put(60005 ,"不允许删除有成员的部门");
        errorCodeMap.put(60006 ,"不允许删除有子部门的部门");
        errorCodeMap.put(60007 ,"不允许删除根部门");
        errorCodeMap.put(60008 ,"部门ID或者部门名称已存在");
        errorCodeMap.put(60009 ,"部门名称含有非法字符");
        errorCodeMap.put(60010 ,"部门存在循环关系");
        errorCodeMap.put(60011 ,"权限不足，user/department/agent无权限(只有通迅录同步助手才有通迅录写权限，同时要开启写权限 )");
        errorCodeMap.put(60012 ,"不允许删除默认应用");
        errorCodeMap.put(60013 ,"不允许关闭应用");
        errorCodeMap.put(60014 ,"不允许开启应用");
        errorCodeMap.put(60015 ,"不允许修改默认应用可见范围");
        errorCodeMap.put(60016 ,"不允许删除存在成员的标签");
        errorCodeMap.put(60017 ,"不允许设置企业");
        errorCodeMap.put(60019 ,"不允许设置应用地理位置上报开关");
        errorCodeMap.put(60020 ,"访问ip不在白名单之中");
        errorCodeMap.put(60023 ,"已授权的应用不允许企业管理组调用接口修改菜单");
        errorCodeMap.put(60025 ,"主页型应用不支持的消息类型");
        errorCodeMap.put(60027 ,"不支持第三方修改主页型应用字段");
        errorCodeMap.put(60028 ,"应用已授权予第三方，不允许通过接口修改主页url");
        errorCodeMap.put(60029 ,"应用已授权予第三方，不允许通过接口修改可信域名");
        errorCodeMap.put(60031 ,"未设置管理组的登录授权域名");
        errorCodeMap.put(60102 ,"UserID已存在");
        errorCodeMap.put(60103 ,"手机号码不合法");
        errorCodeMap.put(60104 ,"手机号码已存在");
        errorCodeMap.put(60105 ,"邮箱不合法");
        errorCodeMap.put(60106 ,"邮箱已存在");
        errorCodeMap.put(60107 ,"微信号不合法");
        errorCodeMap.put(60108 ,"微信号已存在");
        errorCodeMap.put(60109 ,"QQ号已存在");
        errorCodeMap.put(60110 ,"用户同时归属部门超过20个");
        errorCodeMap.put(60111 ,"UserID不存在");
        errorCodeMap.put(60112 ,"成员姓名不合法");
        errorCodeMap.put(60113 ,"身份认证信息（微信号/手机/邮箱）不能同时为空");
        errorCodeMap.put(60114 ,"性别不合法");
        errorCodeMap.put(60115 ,"已关注成员微信不能修改");
        errorCodeMap.put(60116 ,"扩展属性已存在");
        errorCodeMap.put(60118 ,"成员无有效邀请字段，详情参考(邀请成员关注)的接口说明");
        errorCodeMap.put(60119 ,"成员已关注");
        errorCodeMap.put(60120 ,"成员已禁用");
        errorCodeMap.put(60121 ,"找不到该成员");
        errorCodeMap.put(60122 ,"邮箱已被外部管理员使用");
        errorCodeMap.put(60123 ,"无效的部门id");
        errorCodeMap.put(60124 ,"无效的父部门id");
        errorCodeMap.put(60125 ,"非法部门名字，长度超过限制、重名等，重名包括与csv文件中同级部门重名或者与旧组织架构包含成员的同级部门重名");
        errorCodeMap.put(60126 ,"创建部门失败");
        errorCodeMap.put(60127 ,"缺少部门id");
        errorCodeMap.put(60128 ,"字段不合法，可能存在主键冲突或者格式错误");
        errorCodeMap.put(60129 ,"用户设置了拒绝邀请");
        errorCodeMap.put(60131 ,"不合法的职位长度");
        errorCodeMap.put(80001 ,"可信域名不匹配，或者可信域名没有IPC备案（后续将不能在该域名下正常使用jssdk）");
        errorCodeMap.put(81001 ,"部门下的结点数超过限制（3W）");
        errorCodeMap.put(81003 ,"邀请额度已用完");
        errorCodeMap.put(81004 ,"部门数量超过上限");
        errorCodeMap.put(82001 ,"发送消息或者邀请的参数全部为空或者全部不合法");
        errorCodeMap.put(82002 ,"不合法的PartyID列表长度");
        errorCodeMap.put(82003 ,"不合法的TagID列表长度");
        errorCodeMap.put(82004 ,"微信版本号过低");
        errorCodeMap.put(85002 ,"包含不合法的词语");
        errorCodeMap.put(86001 ,"不合法的会话ID");
        errorCodeMap.put(86003 ,"不存在的会话ID");
        errorCodeMap.put(86004 ,"不合法的会话名");
        errorCodeMap.put(86005 ,"不合法的会话管理员");
        errorCodeMap.put(86006 ,"不合法的成员列表大小");
        errorCodeMap.put(86007 ,"不存在的成员");
        errorCodeMap.put(86101 ,"需要会话管理员权限");
        errorCodeMap.put(86201 ,"缺少会话ID");
        errorCodeMap.put(86202 ,"缺少会话名");
        errorCodeMap.put(86203 ,"缺少会话管理员");
        errorCodeMap.put(86204 ,"缺少成员");
        errorCodeMap.put(86205 ,"非法的会话ID长度");
        errorCodeMap.put(86206 ,"非法的会话ID数值");
        errorCodeMap.put(86207 ,"会话管理员不在用户列表中");
        errorCodeMap.put(86208 ,"消息服务未开启");
        errorCodeMap.put(86209 ,"缺少操作者");
        errorCodeMap.put(86210 ,"缺少会话参数");
        errorCodeMap.put(86211 ,"缺少会话类型（单聊或者群聊）");
        errorCodeMap.put(86213 ,"缺少发件人");
        errorCodeMap.put(86214 ,"非法的会话类型");
        errorCodeMap.put(86215 ,"会话已存在");
        errorCodeMap.put(86216 ,"非法会话成员");
        errorCodeMap.put(86217 ,"会话操作者不在成员列表中");
        errorCodeMap.put(86218 ,"非法会话发件人");
        errorCodeMap.put(86219 ,"非法会话收件人");
        errorCodeMap.put(86220 ,"非法会话操作者");
        errorCodeMap.put(86221 ,"单聊模式下，发件人与收件人不能为同一人");
        errorCodeMap.put(86222 ,"不允许消息服务访问的API");
        errorCodeMap.put(86304 ,"不合法的消息类型");
        errorCodeMap.put(86305 ,"客服服务未启用");
        errorCodeMap.put(86306 ,"缺少发送人");
        errorCodeMap.put(86307 ,"缺少发送人类型");
        errorCodeMap.put(86308 ,"缺少发送人id");
        errorCodeMap.put(86309 ,"缺少接收人");
        errorCodeMap.put(86310 ,"缺少接收人类型");
        errorCodeMap.put(86311 ,"缺少接收人id");
        errorCodeMap.put(86312 ,"缺少消息类型");
        errorCodeMap.put(86313 ,"缺少客服，发送人或接收人类型，必须有一个为kf");
        errorCodeMap.put(86314 ,"客服不唯一，发送人或接收人类型，必须只有一个为kf");
        errorCodeMap.put(86315 ,"不合法的发送人类型");
        errorCodeMap.put(86316 ,"不合法的发送人id。Userid不存在、openid不存在、kf不存在");
        errorCodeMap.put(86317 ,"不合法的接收人类型");
        errorCodeMap.put(86318 ,"不合法的接收人id。Userid不存在、openid不存在、kf不存在");
        errorCodeMap.put(86319 ,"不合法的客服，kf不在客服列表中");
        errorCodeMap.put(86320 ,"不合法的客服类型");
        errorCodeMap.put(88001 ,"缺少seq参数");
        errorCodeMap.put(88002 ,"缺少offset参数");
        errorCodeMap.put(88003 ,"非法seq");
        errorCodeMap.put(90001 ,"未认证摇一摇周边");
        errorCodeMap.put(90002 ,"缺少摇一摇周边ticket参数");
        errorCodeMap.put(90003 ,"摇一摇周边ticket参数不合法");
        errorCodeMap.put(90004 ,"摇一摇周边ticket过期");
        errorCodeMap.put(90005 ,"未开启摇一摇周边服务");
        errorCodeMap.put(91004 ,"卡券已被核销");
        errorCodeMap.put(91011 ,"无效的code");
        errorCodeMap.put(91014 ,"缺少卡券详情");
        errorCodeMap.put(91015 ,"代金券缺少least_cost或者reduce_cost参数");
        errorCodeMap.put(91016 ,"折扣券缺少discount参数");
        errorCodeMap.put(91017 ,"礼品券缺少gift参数");
        errorCodeMap.put(91019 ,"缺少卡券sku参数");
        errorCodeMap.put(91020 ,"缺少卡券有效期");
        errorCodeMap.put(91021 ,"缺少卡券有效期类型");
        errorCodeMap.put(91022 ,"缺少卡券logo_url");
        errorCodeMap.put(91023 ,"缺少卡券code类型");
        errorCodeMap.put(91025 ,"缺少卡券title");
        errorCodeMap.put(91026 ,"缺少卡券color");
        errorCodeMap.put(91027 ,"缺少offset参数");
        errorCodeMap.put(91028 ,"缺少count参数");
        errorCodeMap.put(91029 ,"缺少card_id");
        errorCodeMap.put(91030 ,"缺少卡券code");
        errorCodeMap.put(91031 ,"缺少卡券notice");
        errorCodeMap.put(91032 ,"缺少卡券description");
        errorCodeMap.put(91033 ,"缺少ticket类型");
        errorCodeMap.put(91036 ,"不合法的有效期");
        errorCodeMap.put(91038 ,"变更库存值不合法");
        errorCodeMap.put(91039 ,"不合法的卡券id");
        errorCodeMap.put(91040 ,"不合法的ticket type");
        errorCodeMap.put(91041 ,"没有创建，上传卡券logo，以及核销卡券的权限");
        errorCodeMap.put(91042 ,"没有该卡券投放权限");
        errorCodeMap.put(91043 ,"没有修改或者删除该卡券的权限");
        errorCodeMap.put(91044 ,"不合法的卡券参数");
        errorCodeMap.put(91045 ,"缺少团购券groupon结构");
        errorCodeMap.put(91046 ,"缺少现金券cash结构");
        errorCodeMap.put(91047 ,"缺少折扣券discount 结构");
        errorCodeMap.put(91048 ,"缺少礼品券gift结构");
        errorCodeMap.put(91049 ,"缺少优惠券coupon结构");
        errorCodeMap.put(91050 ,"缺少卡券必填字段");
        errorCodeMap.put(91051 ,"商户名称超过12个汉字");
        errorCodeMap.put(91052 ,"卡券标题超过9个汉字");
        errorCodeMap.put(91053 ,"卡券提醒超过16个汉字");
        errorCodeMap.put(91054 ,"卡券描述超过1024个汉字");
        errorCodeMap.put(91055 ,"卡券副标题长度超过18个汉字");


        errorCodeMap.put(301001," 应用id已存在");
        errorCodeMap.put(301002," accesstoken不允许操作其它应用。");
        errorCodeMap.put(301004," 不允许删除超级管理员");
        errorCodeMap.put(301005," 消息型应用不允许做此操作");
        errorCodeMap.put(301006," 不允许禁用超级管理员");
        errorCodeMap.put(301008," 主页型应用不允许做此操作");
        errorCodeMap.put(301009," 应用发送消息没有接收主体");
        errorCodeMap.put(301010," 部门名已存在");
        errorCodeMap.put(301012," 职位信息不合法");
        errorCodeMap.put(301013," 座机不合法");
        errorCodeMap.put(301014," 英文名称不合法");
        errorCodeMap.put(301021," userid错误");
        errorCodeMap.put(301022," 获取打卡数据失败");
        errorCodeMap.put(301023," useridlist非法或超过限额");
        errorCodeMap.put(301024," 获取打卡记录时间间隔超限");
        errorCodeMap.put(302001," 批量同步成员存在userid为空的用户");
        errorCodeMap.put(302002," 管理员userid不存在");
        errorCodeMap.put(302003," 存在重复的userid");
        errorCodeMap.put(302004," 组织架构不合法");
    }

    @Value("${wx.cropId}")
    private String cropId;

    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.addressBook.secret}")
    private String addressBookSecret;
    @Value("${wx.app.agentId}")
    private Integer agentId;

    /**
     * Guava缓存 将AccessToken缓存7200秒
     */
    private LoadingCache<String,String> accessTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    String url;
                    if(TOKEN_TYPE_ADDRESS_BOOK.equals(s)) {
                        url = MessageFormat.format(ACCESS_TOKEN_URL,cropId,addressBookSecret);
                    } else {
                        url = MessageFormat.format(ACCESS_TOKEN_URL,cropId,secret);
                    }

                    String result = sendGetRequest(url);
                    //JSON -> Map
                    Map<String,Object> map = JSON.parseObject(result, HashMap.class);
                    Integer errorCode = Integer.valueOf(map.get("errcode").toString());
                    if(errorCode.equals(0)) {
                        return map.get("access_token").toString();
                    } else {
                        throw new WeiXinException("获取AccessToken异常，错误码:" + errorCode + " 错误原因：" + errorCodeMap.get(errorCode));
                    }
                }
            });



    /**
     * 获取企业微信的AccessToken
     * @return
     */
    public String getAccessToken(String type) {
        try {
            return accessTokenCache.get(type);
        } catch (ExecutionException e) {
            throw new WeiXinException(e);
        }
    }


    /**
     * 创建微信部门
     * @param deptId 部门ID
     * @param parentId 部门的父ID
     * @param name 部门名称
     */
    public void createDept(int deptId,int parentId,String name) {
        String url = MessageFormat.format(CREATE_DEPT_URL,getAccessToken(TOKEN_TYPE_ADDRESS_BOOK));

        Map<String,Object> param = Maps.newHashMap();
        param.put("name",name);
        param.put("parentid",parentId);
        param.put("id",deptId);

        String result = sendPostRequest(url,param);
        //JSON -> Map
        Map<String,Object> map = JSON.parseObject(result, HashMap.class);
        Integer errorCode = Integer.valueOf(map.get("errcode").toString());
        if(!errorCode.equals(0)) {
            throw new WeiXinException("创建部门异常，错误码:" + errorCode + " 错误原因：" + errorCodeMap.get(errorCode));
        }
    }


    /**
     * 删除部门
     * @param deptId
     */
    public void deleteDeptById(String deptId) {
        String url = MessageFormat.format(DELETE_DEPT_URL,getAccessToken(TOKEN_TYPE_ADDRESS_BOOK),deptId);
        String result = sendGetRequest(url);
        //JSON -> Map
        Map<String,Object> map = JSON.parseObject(result, HashMap.class);
        Integer errorCode = Integer.valueOf(map.get("errcode").toString());
        if(!errorCode.equals(0)) {
            throw new WeiXinException("删除部门异常，错误码:" + errorCode + " 错误原因：" + errorCodeMap.get(errorCode));
        }
    }

    /**
     * 创建用户
     * @param userId 用户ID
     * @param userName 用户名称
     * @param deptIds 所在部门id数组
     * @param mobile 手机号码
     */
    public void createAccount(int userId,String userName,Integer[] deptIds,String mobile) {
        String url = MessageFormat.format(CREATE_ACCOUNT_URL,getAccessToken(TOKEN_TYPE_ADDRESS_BOOK));
        Map<String,Object> param = Maps.newHashMap();
        param.put("userid",userId);
        param.put("name",userName);
        param.put("mobile",mobile);
        param.put("department",deptIds);

        String result = sendPostRequest(url,param);
        //JSON -> Map
        Map<String,Object> map = JSON.parseObject(result, HashMap.class);
        Integer errorCode = Integer.valueOf(map.get("errcode").toString());
        if(!errorCode.equals(0)) {
            throw new WeiXinException("创建用户异常，错误码:" + errorCode + " 错误原因：" + errorCodeMap.get(errorCode));
        }

    }

    /**
     * 删除账号
     * @param accountId 用户ID
     */
    public void deleteAccountById(String accountId) {
        String url = MessageFormat.format(DELETE_ACCOUNT_URL,getAccessToken(TOKEN_TYPE_ADDRESS_BOOK),accountId);
        String result = sendGetRequest(url);
        //JSON -> Map
        Map<String,Object> map = JSON.parseObject(result, HashMap.class);
        Integer errorCode = Integer.valueOf(map.get("errcode").toString());
        if(!errorCode.equals(0)) {
            throw new WeiXinException("删除用户异常，错误码:" + errorCode + " 错误原因：" + errorCodeMap.get(errorCode));
        }
    }

    /**
     * 给指定用户发送文本消息
     * @param userIds 接受消息的用户ID
     * @param message 消息内容
     */
    public void sendTextMessageToUser(String message,String... userIds) {
        StringBuilder sb = new StringBuilder("");
        for(String id : userIds) {
            sb.append(id).append("|");
        }
        String idStr = sb.toString();
        idStr = idStr.substring(0,idStr.length()-1);
        sendTextMessage(message,"toUser",idStr);
    }

    /**
     * 给指定部门发送文本消息
     * @param deptIds 接受消息的部门ID
     * @param message 消息内容
     */
    public void sendTextMessageToDept(String message,String... deptIds) {
        StringBuilder sb = new StringBuilder("");
        for(String id : deptIds) {
            sb.append(id).append("|");
        }
        String idStr = sb.toString();
        idStr = idStr.substring(0,idStr.length()-1);
        sendTextMessage(message,"toDept",idStr);
    }

    private void sendTextMessage(String message,String type,String ids) {
        String url = MessageFormat.format(SEND_MESSAGE_URL,getAccessToken(TOKEN_TYPE_NORMAL));

        Map<String,String> messageMap = Maps.newHashMap();
        messageMap.put("content",message);

        Map<String,Object> param = Maps.newHashMap();
        if("toUser".equals(type)) {
            param.put("touser",ids);
        } else if("toDept".equals(type)) {
            param.put("toparty",ids);
        }
        param.put("msgtype","text");
        param.put("agentid",agentId);
        param.put("text",messageMap);

        String result = sendPostRequest(url,param);
        //JSON -> Map
        Map<String,Object> map = JSON.parseObject(result, HashMap.class);
        Integer errorCode = Integer.valueOf(map.get("errcode").toString());
        if(!errorCode.equals(0)) {
            throw new WeiXinException("发送微信消息异常，错误码:" + errorCode + " 错误原因：" + errorCodeMap.get(errorCode));
        }
    }


    private String sendGetRequest(String url) {
        //1. 创建HttpClient
        OkHttpClient httpClient = new OkHttpClient();
        //2. 创建请求对象
        Request request = new Request.Builder().url(url).build();
        try {
            //3. 发出请求 并获取结果
            Response response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            throw new RuntimeException("请求"+url+"异常",ex);
        }
    }

    private String sendPostRequest(String url,Object obj) {
        //准备Post发送的数据
        String json = JSON.toJSONString(obj); // Object -> JSON
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),json);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build(); //创建Post请求
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            throw new RuntimeException("请求"+url+"异常",ex);
        }
    }
}
