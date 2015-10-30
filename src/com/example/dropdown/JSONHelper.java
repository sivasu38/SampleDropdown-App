package com.example.dropdown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {



	public static ArrayList<String> getJenkinsJobs(String result)
	{
		ArrayList<String> lst = new ArrayList<>();
		try {
			JSONObject obj = new JSONObject(result);
			int jobcnt = obj.getJSONArray("jobs").length();
			for (int i = 0; i < jobcnt; i++) {
				lst.add(obj.getJSONArray("jobs")
						.getJSONObject(i).getString("name"));

			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	public static HashMap<String, String> getBasicJobDetails(String result) {

		HashMap<String,String> map = new HashMap<>();
		try {
			JSONObject obj = new JSONObject(result);
			String lastBuildUrl="";
			String lastCompletedBuildUrl="";
			String lastSuccessfulBuildUrl="";
			String lastUnstableBuildUrl="";
			String lastUnsuccessfulBuildUrl="";
			String healthReport =obj.get("healthReport").toString();
			String lastBuild=obj.get("lastBuild").toString();
			String lastCompletedBuild = obj.get("lastCompletedBuild").toString();
			String lastSuccessfulBuild = obj.get("lastSuccessfulBuild").toString();
			String lastUnstableBuild = obj.get("lastUnstableBuild").toString();
			String lastUnsuccessfulBuild = obj.getString("lastUnsuccessfulBuild").toString();
			String firstBuild = obj.getString("firstBuild").toString();
			if(!healthReport.equals("[]"))
			{
				healthReport=obj.getJSONArray("healthReport").getJSONObject(0).getString("description");
			}
			else
			{
				healthReport="Not Applicable";
			}
			if(!(lastBuild=="null"))
			{
				lastBuild = Integer.toString(obj.getJSONObject("lastBuild").getInt("number"));
				lastBuildUrl = obj.getJSONObject("lastBuild").getString("url");
			}
			else
			{
				lastBuild="Not Applicable";
			}
			if(!(lastCompletedBuild=="null"))
			{
				lastCompletedBuild = Integer.toString(obj.getJSONObject("lastCompletedBuild").getInt("number"));
				lastCompletedBuildUrl = obj.getJSONObject("lastCompletedBuild").getString("url");
			}
			else
			{
				lastCompletedBuild="Not Applicable";
			}
			if(!(lastSuccessfulBuild=="null"))
			{
				lastSuccessfulBuild = Integer.toString(obj.getJSONObject("lastSuccessfulBuild").getInt("number"));
				lastSuccessfulBuildUrl = obj.getJSONObject("lastSuccessfulBuild").getString("url");
			}
			else
			{
				lastSuccessfulBuild="Not Applicable";
			}
			if(!(lastUnstableBuild=="null"))
			{
				lastUnstableBuild = Integer.toString(obj.getJSONObject("lastUnstableBuild").getInt("number"));
				lastUnstableBuildUrl = obj.getJSONObject("lastUnstableBuild").getString("url");
			}
			else
			{
				lastUnstableBuild="Not Applicable";
			}
			if(!(lastUnsuccessfulBuild=="null"))
			{
				lastUnsuccessfulBuild = Integer.toString(obj.getJSONObject("lastUnsuccessfulBuild").getInt("number"));
				lastUnsuccessfulBuildUrl = obj.getJSONObject("lastUnsuccessfulBuild").getString("url");
			}
			else
			{
				lastUnsuccessfulBuild="Not Applicable";
			}
			String nextBuildNumber = Integer.toString(obj.getInt("nextBuildNumber"));
			String inQueue = Boolean.toString(obj.getBoolean("inQueue"));
			if(!(firstBuild=="null"))
			{
				firstBuild = Integer.toString(obj.getJSONObject("firstBuild").getInt("number"));
			}
			map.put("HealthReport",healthReport);
			map.put("lastBuild",lastBuild);
			map.put("lastBuildUrl",lastBuildUrl);
			map.put("lastCompletedBuild",lastCompletedBuild);
			map.put("lastCompletedBuildUrl",lastCompletedBuildUrl);
			map.put("lastSuccessfulBuildUrl",lastSuccessfulBuildUrl);
			map.put("lastSuccessfulBuild",lastSuccessfulBuild);
			map.put("lastUnstableBuild",lastUnstableBuild);
			map.put("lastUnstableBuildUrl",lastUnstableBuildUrl);
			map.put("lastUnsuccessfulBuild",lastUnsuccessfulBuild);
			map.put("lastUnsuccessfulBuildUrl",lastUnsuccessfulBuildUrl);
			map.put("nextBuildNumber",nextBuildNumber);
			map.put("lastSuccessfulBuild",lastSuccessfulBuild);
			map.put("firstBuild",firstBuild);
			map.put("inQueue",inQueue);
			
			if(inQueue.contains("true"))
			{
				String stuck = Boolean.toString(obj.getJSONObject("queueItem").getBoolean("stuck"));
				String why = obj.getJSONObject("queueItem").getString("why");
				String url = obj.getJSONObject("queueItem").getString("url");
				map.put("stuck",stuck);
				map.put("why",why);
				map.put("url",url);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static HashMap<String, String> getIndividualJobDetails(String result) {
		HashMap<String,String> map = new HashMap<>();
		try {
			JSONObject obj = new JSONObject(result);
			String lastBuildStatus = obj.get("result").toString();
			String lastBuildRanAt = obj.get("id").toString();
			String estimatedDuration = obj.get("estimatedDuration").toString();
			String building = obj.get("building").toString();
			String duration = obj.get("duration").toString();
			if(lastBuildStatus=="null")
			{
				lastBuildStatus="In Progress";
			}
			if(duration=="null")
			{
				duration="";
			}
		    map.put("lastBuildStatus", lastBuildStatus);
		    map.put("lastBuildRanAt", lastBuildRanAt);
		    map.put("estDuration", estimatedDuration);
		    map.put("building", building);
		    map.put("duration", duration);
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
		
}
