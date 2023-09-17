package com.acanisue;

import com.acanisue.domain.ResourceDB;
import com.acanisue.domain.Results;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.InputBuffer;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 14:10
 */
public class Test {
		public static void main(String[] args) throws IOException {
				Integer port =8888;
				String host ="172.16.129.18";
				Socket socket = new Socket(host,port);
				OutputStream outputStream = socket.getOutputStream();

				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
				ObjectMapper objectMapper = new ObjectMapper();
				Object url ="https://img2.baidu.com/it/u=1559244147,1221104704&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=751";
				Map<String,Object> map =new HashMap<>();
				map.put("url", url);
				map.put("res", new ResourceDB());
				String json = objectMapper.writeValueAsString(Results.setDealingWithDefault(map).ok());
				outputStreamWriter.write(json, 0, json.length());
				outputStreamWriter.flush();
				InputStream inputStream = socket.getInputStream();
				InputStreamReader reader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(reader);
				String s = bufferedReader.readLine();
				System.out.println(s);
				outputStreamWriter.close();
		}
}
