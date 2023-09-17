package com.acanisue;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 编写者：Acanisue
 * 日期；2023/9/17 10:00
 */
public class Test1 {
		public static void main(String[] args) {
				String s="{\"code\": 200, \"msg\": \"success\", \"data\": {\"result\": [\"overloading\", \"passenger\", \"passenger\", \"driver\", \"overloading\"], \"url\": \"/ai/file?fileName=9efdbdcc-54fd-11ee-b127-72b95aced385.png\"}}";
				try {
						JSONObject jsonObject = new JSONObject(s);
						Object data = jsonObject.get("data");
						System.out.println(data);
				} catch (JSONException e) {
						e.printStackTrace();
				}

		}
}
