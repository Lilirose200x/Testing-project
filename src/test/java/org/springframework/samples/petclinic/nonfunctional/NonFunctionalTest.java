package org.springframework.samples.petclinic.nonfunctional;

import org.springframework.samples.petclinic.model.*;
import java.io.*;
import java.time.LocalDate;
import java.lang.management.*;
import java.net.*;
import java.util.*;

public class NonFunctionalTest {

	private static int ownerId = 1;

	final static String baseUrl = "http://localhost:8080/";
	static int[] petIds = new int[1000];

	private static String randomname() {

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();

		Random random = new Random();

		int length = 5;

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		return randomString;

	}

	private static Owner createOwner() {
		Owner owner = new Owner();
		owner.setTelephone("1234567890");
		owner.setLastName(randomname());
		owner.setFirstName(randomname());
		owner.setCity("Montreal");
		owner.setAddress(randomname());
		return owner;
	}

	public static Pet createPet() {
		Pet pet = new Pet();
		pet.setName(randomname());
		pet.setBirthDate(LocalDate.parse("2020-06-01"));
		PetType type = new PetType();
		type.setName("cat");
		pet.setType(type);
		return pet;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		boolean check = false;
		try {
			URL url = new URL(baseUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.disconnect();
			check = true;
		}
		catch (IOException e) {
			check = false;
		}
		if (check == true) {
			testCreateOwner500();
			testUpdateOwner500();
			testCreatePet500();
			testUpdatePet500();
			testCreateOwner();
			testUpdateOwner();
			testCreatePet();
			testUpdatePet();
		}
		else {
			System.out.println("Your System is not running");
		}

	}

	public static void testCreateOwner500() throws IOException {
		FileWriter newFile = new FileWriter("createOwner500.csv");
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Size: " + 500);
		long t1;
		long t2 = 0;
		double averageTime;
		double totalCpu = 0.0;
		double averageCpu;
		double totalMemory = 0.0;
		double averageMemory;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 500; i++) {
			long startTimeT1 = System.currentTimeMillis();
			double memory;
			double cpu;

			Owner owner = createOwner();
			HashMap<String, String> params = new HashMap<>();
			params.put("firstName", owner.getFirstName());
			params.put("lastName", owner.getLastName());
			params.put("address", owner.getAddress());
			params.put("city", owner.getCity());
			params.put("telephone", owner.getTelephone());
			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> item : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String path = "owners/" + "new";
			sendRequest("POST", baseUrl, path, postDataBytes);
			sendRequest("GET", baseUrl, "owners/" + i, postDataBytes);

			petIds[i] = i;
			long endTimeT1 = System.currentTimeMillis();
			t1 = endTimeT1 - startTimeT1;
			long time_until_now=endTimeT1-startTime;
			t2 += t1;
			cpu = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuLoad();
			memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			totalCpu += cpu;
			totalMemory += memory;
			List<String> result = Arrays.asList(String.valueOf(t1), String.valueOf(cpu * 100.0),
					String.valueOf(memory),String.valueOf(time_until_now));
			newFile.append(String.join(",", result));
			newFile.append("\n");
		}
		newFile.flush();
		newFile.close();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		averageTime = (double) t2 / 500;
		averageCpu = totalCpu / (double) 500 * 100.0;
		averageMemory = totalMemory / (double) 500;
		System.out.println("Average time: " + averageTime + " ms Average CPU: " + averageCpu
				+ " % Average memory usage: " + averageMemory + " bytes");
		System.out.println("Total Time: " + totalTime + " ms");
		ownerId++;

	}

	public static void testUpdateOwner500() throws IOException {
		FileWriter newFile = new FileWriter("updateOwner500.csv");
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Size: " + 500);
		long t1;
		long t2 = 0;
		double averageTime;
		double totalCpu = 0.0;
		double averageCpu;
		double totalMemory = 0.0;
		double averageMemory;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 500; i++) {
			long startTimeT1 = System.currentTimeMillis();
			double memory;
			double cpu;
			int currId = petIds[i];

			Owner owner = createOwner();
			HashMap<String, String> params = new HashMap<>();
			params.put("firstName", owner.getFirstName());
			params.put("lastName", owner.getLastName());
			params.put("address", owner.getAddress());
			params.put("city", owner.getCity());
			params.put("telephone", owner.getTelephone());
			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> item : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String path = "owners/" + currId + "/edit";

			sendRequest("POST", baseUrl, path, postDataBytes);
			sendRequest("GET", baseUrl, "owners/" + currId, postDataBytes);

			long endTimeT1 = System.currentTimeMillis();
			t1 = endTimeT1 - startTimeT1;
			long time_until_now=endTimeT1-startTime;
			t2 += t1;
			cpu = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuLoad();
			memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			totalCpu += cpu;
			totalMemory += memory;
			List<String> result = Arrays.asList(String.valueOf(t1), String.valueOf(cpu * 100.0),
					String.valueOf(memory),String.valueOf(time_until_now));
			newFile.append(String.join(",", result));
			newFile.append("\n");
		}
		newFile.flush();
		newFile.close();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		averageTime = (double) t2 / 500;
		averageCpu = totalCpu / (double) 500 * 100.0;
		averageMemory = totalMemory / (double) 500;
		System.out.println("Average time: " + averageTime + " ms Average CPU: " + averageCpu
				+ " % Average memory usage: " + averageMemory + " bytes");
		System.out.println("Total Time: " + totalTime + " ms");
		ownerId++;

	}

	public static void testCreatePet500() throws IOException {
		FileWriter newFile = new FileWriter("createpet500.csv");

		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Size: " + 500);
		long t1;
		long t2 = 0;
		double averageTime;
		double totalCpu = 0.0;
		double averageCpu;
		double totalMemory = 0.0;
		double averageMemory;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 500; i++) {
			long startTimeT1 = System.currentTimeMillis();
			double memory = 0;
			double cpu = 0;
			Pet pet = createPet();
			HashMap<String, String> params = new HashMap<>();
			params.put("name", pet.getName());
			params.put("birthDate", pet.getBirthDate().toString());
			params.put("type", pet.getType().getName());
			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> item : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String path = "owners/" + ownerId + "/pets/new";
			sendRequest("POST", baseUrl, path, postDataBytes);
			sendRequest("GET", baseUrl, "owners/" + ownerId, postDataBytes);

			petIds[i] = i;
			long endTimeT1 = System.currentTimeMillis();
			t1 = endTimeT1 - startTimeT1;
			long time_until_now=endTimeT1-startTime;
			t2 += t1;
			cpu = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuLoad();
			memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			totalCpu += cpu;
			totalMemory += memory;
			List<String> result = Arrays.asList(String.valueOf(t1), String.valueOf(cpu * 100.0),
					String.valueOf(memory),String.valueOf(time_until_now));
			newFile.append(String.join(",", result));
			newFile.append("\n");
		}
		newFile.flush();
		newFile.close();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		averageTime = (double) t2 / 500;
		averageCpu = totalCpu / (double) 500 * 100.0;
		averageMemory = totalMemory / (double) 500;
		System.out.println("Average time: " + averageTime + " ms Average CPU: " + averageCpu
				+ " % Average memory usage: " + averageMemory + " bytes");
		System.out.println("Total Time: " + totalTime + " ms");
		ownerId++;

	}

	public static void testUpdatePet500() throws IOException {
		FileWriter newFile = new FileWriter("updatepet500.csv");
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Size: " + 500);
		long t1;
		long t2 = 0;
		double averageTime;
		double totalCpu = 0.0;
		double averageCpu;
		double totalMemory = 0.0;
		double averageMemory;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 500; i++) {
			long startTimeT1 = System.currentTimeMillis();
			double memory;
			double cpu;
			int currId = petIds[i];
			Pet pet = createPet();
			HashMap<String, String> params = new HashMap<>();
			params.put("name", pet.getName());
			params.put("birthDate", pet.getBirthDate().toString());
			params.put("type", pet.getType().getName());
			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> item : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String path = "owners/" + ownerId + "/pets/" + currId + "/edit";
			sendRequest("POST", baseUrl, path, postDataBytes);
			sendRequest("GET", baseUrl, "owners/" + ownerId, postDataBytes);

			long endTimeT1 = System.currentTimeMillis();
			t1 = endTimeT1 - startTimeT1;
			long time_until_now=endTimeT1-startTime;
			t2 += t1;
			cpu = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuLoad();
			memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			totalCpu += cpu;
			totalMemory += memory;
			List<String> result = Arrays.asList(String.valueOf(t1), String.valueOf(cpu * 100.0),
					String.valueOf(memory),String.valueOf(time_until_now));
			newFile.append(String.join(",", result));
			newFile.append("\n");
		}
		newFile.flush();
		newFile.close();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		averageTime = (double) t2 / 500;
		averageCpu = totalCpu / (double) 500 * 100.0;
		averageMemory = totalMemory / (double) 500;
		System.out.println("Average time: " + averageTime + " ms Average CPU: " + averageCpu
				+ " % Average memory usage: " + averageMemory + " bytes");
		System.out.println("Total Time: " + totalTime + " ms");
		ownerId++;

	}

	public static void testCreateOwner() throws IOException {
		FileWriter newFile = new FileWriter("createOwner1000.csv");
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Size: " + 1000);
		long t1;
		long t2 = 0;
		double averageTime;
		double totalCpu = 0.0;
		double averageCpu;
		double totalMemory = 0.0;
		double averageMemory;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			long startTimeT1 = System.currentTimeMillis();
			double memory;
			double cpu;

			Owner owner = createOwner();
			HashMap<String, String> params = new HashMap<>();
			params.put("firstName", owner.getFirstName());
			params.put("lastName", owner.getLastName());
			params.put("address", owner.getAddress());
			params.put("city", owner.getCity());
			params.put("telephone", owner.getTelephone());
			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> item : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String path = "owners/" + "new";
			sendRequest("POST", baseUrl, path, postDataBytes);
			sendRequest("GET", baseUrl, "owners/" + i, postDataBytes);

			petIds[i] = i;
			long endTimeT1 = System.currentTimeMillis();
			t1 = endTimeT1 - startTimeT1;
			long time_until_now=endTimeT1-startTime;
			t2 += t1;
			cpu = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuLoad();
			memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			totalCpu += cpu;
			totalMemory += memory;
			List<String> result = Arrays.asList(String.valueOf(t1), String.valueOf(cpu * 100.0),
					String.valueOf(memory),String.valueOf(time_until_now));
			newFile.append(String.join(",", result));
			newFile.append("\n");
		}
		newFile.flush();
		newFile.close();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		averageTime = (double) t2 / 1000;
		averageCpu = totalCpu / (double) 1000 * 100.0;
		averageMemory = totalMemory / (double) 1000;
		System.out.println("Average time: " + averageTime + " ms Average CPU: " + averageCpu
				+ " % Average memory usage: " + averageMemory + " bytes");
		System.out.println("Total Time: " + totalTime + " ms");
		ownerId++;

	}

	public static void testUpdateOwner() throws IOException {
		FileWriter newFile = new FileWriter("updateOwner1000.csv");
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Size: " + 1000);
		long t1;
		long t2 = 0;
		double averageTime;
		double totalCpu = 0.0;
		double averageCpu;
		double totalMemory = 0.0;
		double averageMemory;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			long startTimeT1 = System.currentTimeMillis();
			double memory;
			double cpu;
			int currId = petIds[i];

			Owner owner = createOwner();
			HashMap<String, String> params = new HashMap<>();
			params.put("firstName", owner.getFirstName());
			params.put("lastName", owner.getLastName());
			params.put("address", owner.getAddress());
			params.put("city", owner.getCity());
			params.put("telephone", owner.getTelephone());
			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> item : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String path = "owners/" + currId + "/edit";
			sendRequest("POST", baseUrl, path, postDataBytes);
			sendRequest("GET", baseUrl, "owners/" + currId, postDataBytes);

			long endTimeT1 = System.currentTimeMillis();
			t1 = endTimeT1 - startTimeT1;
			long time_until_now=endTimeT1-startTime;
			t2 += t1;
			cpu = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuLoad();
			memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			totalCpu += cpu;
			totalMemory += memory;
			List<String> result = Arrays.asList(String.valueOf(t1), String.valueOf(cpu * 100.0),
					String.valueOf(memory),String.valueOf(time_until_now));
			newFile.append(String.join(",", result));
			newFile.append("\n");
		}
		newFile.flush();
		newFile.close();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		averageTime = (double) t2 / 1000;
		averageCpu = totalCpu / (double) 1000 * 100.0;
		averageMemory = totalMemory / (double) 1000;
		System.out.println("Average time: " + averageTime + " ms Average CPU: " + averageCpu
				+ " % Average memory usage: " + averageMemory + " bytes");
		System.out.println("Total Time: " + totalTime + " ms");
		ownerId++;

	}

	public static void testCreatePet() throws IOException {
		FileWriter newFile = new FileWriter("createpet1000.csv");

		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Size: " + 1000);
		long t1;
		long t2 = 0;
		double averageTime;
		double totalCpu = 0.0;
		double averageCpu;
		double totalMemory = 0.0;
		double averageMemory;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			long startTimeT1 = System.currentTimeMillis();
			double memory = 0;
			double cpu = 0;
			Pet pet = createPet();
			HashMap<String, String> params = new HashMap<>();
			params.put("name", pet.getName());
			params.put("birthDate", pet.getBirthDate().toString());
			params.put("type", pet.getType().getName());
			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> item : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String path = "owners/" + ownerId + "/pets/new";
			sendRequest("POST", baseUrl, path, postDataBytes);
			sendRequest("GET", baseUrl, "owners/" + ownerId, postDataBytes);

			petIds[i] = i;
			long endTimeT1 = System.currentTimeMillis();
			t1 = endTimeT1 - startTimeT1;
			long time_until_now=endTimeT1-startTime;
			t2 += t1;
			cpu = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuLoad();
			memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			totalCpu += cpu;
			totalMemory += memory;
			List<String> result = Arrays.asList(String.valueOf(t1), String.valueOf(cpu * 100.0),
					String.valueOf(memory),String.valueOf(time_until_now));
			newFile.append(String.join(",", result));
			newFile.append("\n");
		}
		newFile.flush();
		newFile.close();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		averageTime = (double) t2 / 1000;
		averageCpu = totalCpu / (double) 1000 * 100.0;
		averageMemory = totalMemory / (double) 1000;
		System.out.println("Average time: " + averageTime + " ms Average CPU: " + averageCpu
				+ " % Average memory usage: " + averageMemory + " bytes");
		System.out.println("Total Time: " + totalTime + " ms");
		ownerId++;

	}

	public static void testUpdatePet() throws IOException {
		FileWriter newFile = new FileWriter("updatepet1000.csv");
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Size: " + 1000);
		long t1;
		long t2 = 0;
		double averageTime;
		double totalCpu = 0.0;
		double averageCpu;
		double totalMemory = 0.0;
		double averageMemory;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			long startTimeT1 = System.currentTimeMillis();
			double memory;
			double cpu;
			int currId = petIds[i];
			Pet pet = createPet();
			HashMap<String, String> params = new HashMap<>();
			params.put("name", pet.getName());
			params.put("birthDate", pet.getBirthDate().toString());
			params.put("type", pet.getType().getName());
			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> item : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String path = "owners/" + ownerId + "/pets/" + currId + "/edit";
			sendRequest("POST", baseUrl, path, postDataBytes);
			sendRequest("GET", baseUrl, "owners/" + ownerId, postDataBytes);

			long endTimeT1 = System.currentTimeMillis();
			t1 = endTimeT1 - startTimeT1;
			long time_until_now=endTimeT1-startTime;
			t2 += t1;
			cpu = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuLoad();
			memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			totalCpu += cpu;
			totalMemory += memory;
			List<String> result = Arrays.asList(String.valueOf(t1), String.valueOf(cpu * 100.0),
					String.valueOf(memory),String.valueOf(time_until_now));
			newFile.append(String.join(",", result));
			newFile.append("\n");
		}
		newFile.flush();
		newFile.close();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		averageTime = (double) t2 / 1000;
		averageCpu = totalCpu / (double) 1000 * 100.0;
		averageMemory = totalMemory / (double) 1000;
		System.out.println("Average time: " + averageTime + " ms Average CPU: " + averageCpu
				+ " % Average memory usage: " + averageMemory + " bytes");
		System.out.println("Total Time: " + totalTime + " ms");
		ownerId++;

	}

	private static void sendRequest(String request, String baseUrl, String path, byte[] postDataBytes) {
		try {
			URL url = new URL(baseUrl + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setInstanceFollowRedirects(true);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(request);
			connection.setRequestProperty("Accept", "text/html");
			connection.setRequestProperty("charset", "UTF-8");
			if (request != "GET") {
				connection.getOutputStream().write(postDataBytes);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String response = br.readLine();
			if (response != null) {
				connection.disconnect();
			}
		}
		catch (IOException e) {

		}
	}

}
