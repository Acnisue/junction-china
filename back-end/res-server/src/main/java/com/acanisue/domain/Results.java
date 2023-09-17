package com.acanisue.domain;

import java.io.Serializable;

/**
 * 编写者：Acanisue
 * 日期；2023/3/16 10:01
 */


public class Results implements Serializable {
		public static final Integer Internal_Server_Error = 500;
		public static final Integer NULL = 404;
		public static final Integer FIND = 200;
		public static final Integer CODE_FIND = 204; //验证出错
		//权限不足
		public static final Integer INSUFFICIENT_PERMISSIONS = 401;
		private static final long serialVersionUID = 15161L;
		private Integer code=400;
		private Object data = null;
		private String msg = null;
		private boolean success=false;
		private boolean isPrompt = true;

		public Results(Integer code, Object data, String msg, boolean isPrompt, boolean success) {
				this.code = code;
				this.data = data;
				this.msg = msg;
				this.isPrompt = isPrompt;
				this.success=success;
		}

		public Results(Integer code, String msg, boolean isPrompt, boolean success) {
				this.code = code;
				this.msg = msg;
				this.isPrompt = isPrompt;
				this.success=success;
		}

		public Results(Integer code, Object data, boolean isPrompt, boolean success) {
				this.code = code;
				this.data = data;
				this.isPrompt = isPrompt;
				this.success=success;
		}

		public Results() {
		}

		public static Results getInstance() {
				return new Results();
		}

		public Results ok(){
				this.code=200;
				this.success=true;
				return this;
		}

		/**
		 * 设置处理空
		 *
		 * @param isPrompt 是否提示 ？ 默认提示
		 * @param msg      消息
		 * @return {@link Results}
		 */
		public static Results setDealingWithEmpty(boolean isPrompt, String msg) {
				return setDealingWithEmptyDefault().setPrompt(isPrompt)
								 .setMsg(msg);
		}

		/**
		 * 设置有值处理默认
		 *
		 * @param msg      消息
		 * @param isPrompt 是提示 默认提示
		 * @param data     数据
		 * @return {@link Results}
		 */
		public static Results setDealingWithDefault(String msg, boolean isPrompt, Object data) {
				return setDealingWithDefaultCode(404).setMsg(msg)
								 .setPrompt(isPrompt)
								 .setData(data);
		}

		/**
		 * 设置有值处理默认 不设置消息，只设置值,默认提示
		 *
		 * @param data 数据
		 * @return {@link Results}
		 */
		public static Results setDealingWithDefault(Object data) {
				return setDealingWithDefault(null, true, data);
		}

		/**
		 * 设置有值处理默认 不设置消息，只设置值,不提示
		 *
		 * @param data 数据
		 * @return {@link Results}
		 */
		public static Results setDealingWithDefaultNoPrompt(Object data) {
				return setDealingWithDefault(null, false, data);
		}

		/**
		 * 设置消息处理默认 200
		 *
		 * @param msg 消息
		 * @return {@link Results}
		 */
		public static Results setDealingWithDefault(String msg) {
				return setDealingWithDefault(msg, true, null);
		}

		/**
		 * 设置处理默认 消息 data
		 * @param msg 消息
		 * @param ob  data
		 * @return {@link Results}
		 */
		public static Results setDealingWithDefault(String msg,Object ob) {
				return setDealingWithDefault(msg, true, ob);
		}

		/**
		 * 设置空处理没有消息
		 *
		 * @param isPrompt 是否提示 ？ 默认提示
		 * @return {@link Results}
		 */
		public static Results setDealingWithEmptyNoMsg(boolean isPrompt) {
				return setDealingWithEmpty(isPrompt, null);
		}

		/**
		 * 设置空处理 且默认值
		 *
		 * @return {@link Results}
		 */
		public static Results setDealingWithEmptyDefault() {
				return setDealingWithDefaultCode(NULL);
		}

		/**
		 * 设置空处理 并设置消息 并提示
		 *
		 * @param msg 消息
		 * @return {@link Results}
		 */
		public static Results setDealingWithEmptyMsg(String msg) {
				return setDealingWithEmptyDefault().setMsg(msg);
		}

		public static Results setDealingWithEmptyData(Object data) {
				return setDealingWithEmptyDefault().setData(data);
		}

		/**
		 * 设置处理默认并设置代码
		 *
		 * @param code 代码
		 * @return {@link Results}
		 */
		public static Results setDealingWithDefaultCode(Integer code) {
				return new Results().setCode(code);
		}

		public Integer getCode() {
				return code;
		}

		public Results setCode(int code) {
				this.code = code;
				return this;
		}

		public Object getData() {
				return data;
		}

		/**
		 * 设置数据
		 *
		 * @param data 数据
		 * @return {@link Results}
		 */
		public Results setData(Object data) {
				this.data = data;
				return this;
		}

		public String getMsg() {
				return msg;
		}



		public Results setMsg(String msg) {
				this.msg = msg;
				return this;
		}

		public boolean isPrompt() {
				return isPrompt;
		}

		public Results setPrompt(boolean prompt) {
				this.isPrompt = prompt;
				return this;
		}
		public Results setSuccess(boolean success) {
				this.success = success;
				return this;
		}public boolean getSuccess() {
				return success;
		}

		@Override
		public String toString() {
				return "Results{" +
								 "code=" + code +
								 ", data=" + data +
								 ", msg='" + msg + '\'' +
								 ", success=" + success +
								 ", isPrompt=" + isPrompt +
								 '}';
		}
}
