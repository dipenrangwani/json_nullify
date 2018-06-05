package com.tdaux.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JsonNullify {

	public static void main(String[] args) {

		try {
			JSONParser parser = new JSONParser();
			//path of json data file
			JSONObject actualJson = (JSONObject) parser.parse(new FileReader("/home/rangwanidipen/tdaux_project/test_json2"));

			Map jsonData = (Map) jsonDecoder(actualJson.toString());
			
			System.out.println("Actual Json: " + actualJson);
			

			SortedMap data2 = new TreeMap<>();
			data2.putAll(jsonData);

			JsonNullify test = new JsonNullify();
			Map retVal = test.convertData(data2);

			JSONObject convertedJson = new JSONObject(retVal);
			
			System.out.println("Converted Json: " + convertedJson);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Map convertData(Map data) {
		Collection keyset = data.keySet();

		SortedMap keyData = new TreeMap<Integer, String>();

		for (Object object : keyset) {
			// System.out.println(data.get(object) == null);
			if (data.get(object) != null) {
				if (data.get(object).getClass() == boolean.class || data.get(object).getClass() == Boolean.class) {
					
					keyData.put(object, false);
				} else if (data.get(object).getClass() == Integer.class || data.get(object).getClass() == Long.class
						|| data.get(object).getClass() == long.class || data.get(object).getClass() == Short.class
						|| data.get(object).getClass() == short.class) {
					
					keyData.put(object, 0);
				} else if (data.get(object).getClass() == Float.class || data.get(object).getClass() == float.class
						|| data.get(object).getClass() == double.class || data.get(object).getClass() == Double.class) {

					keyData.put(object, 0.0);
				} else if (data.get(object).getClass() == LinkedList.class) {
					
					LinkedList<?> listData = (LinkedList<?>) data.get(object);
					List list = new ArrayList<>();
					
					listData.forEach(item -> {
						if (item.getClass() == LinkedHashMap.class || item.getClass() == Map.class
								|| item.getClass() == HashMap.class) {
							Map d = convertData((Map) item);
							list.add(d);
						} else if (item.getClass() == LinkedList.class) {
							list.add(Collections.EMPTY_LIST);
						}

					});

					keyData.put(object, list);
				} else if (data.get(object).getClass() == LinkedHashMap.class) {
					
					Map mapData = convertData((Map) data.get(object));
					keyData.put(object, mapData);
				}

				else {
					
					keyData.put(object, "");
				}
			} else {
				
				keyData.put(object, null);
			}
		}

		return keyData;
	}

	/*
	 * customized decode method to standardize data
	 * */
	private static Object jsonDecoder(String jsonObject) throws ParseException {

		ContainerFactory factory = new ContainerFactory() {

			@Override
			public Map createObjectContainer() {
				return new LinkedHashMap();
				// return new HashMap();
			}

			@Override
			public List creatArrayContainer() {
				return new LinkedList();
				// return new ArrayList();
			}
		};

		JSONParser parser = new JSONParser();
		Object mainObjectMap = (Object) parser.parse(jsonObject, factory);
		return mainObjectMap;

	}
}