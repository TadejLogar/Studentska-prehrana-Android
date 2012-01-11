package com.sciget.studentmeals.client.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.client.service.data.CommentData;
import com.sciget.studentmeals.client.service.data.Data;
import com.sciget.studentmeals.client.service.data.DataPrimitive;
import com.sciget.studentmeals.client.service.data.FavoritedRestaurantData;
import com.sciget.studentmeals.client.service.data.FileData;
import com.sciget.studentmeals.client.service.data.HistoryData;
import com.sciget.studentmeals.client.service.data.MenuData;
import com.sciget.studentmeals.client.service.data.RestaurantData;
import com.sciget.studentmeals.client.service.data.StudentMealsStateData;
import com.sciget.studentmeals.client.service.data.UserData;

public class StudentMealsService extends WebService {
	public static class LoginDataStates {
		public static final int OK = 1; // ok
		public static final int SESSION_INVALID = 2; // seja kode je potekla
		public static final int STUDENT_MEALS_DATA_INVALID = 3; // napačni prijavni podatki / napačna koda
		public static final int APP_DATA_INVALID = 4; // neveljavni podatki za račun aplikacije
		public static final int CREATE_ACCOUNT_ERROR = 5; // napaka pri ustvarjanju računa aplikacije
		
		public static String toString(int result) {
			if (result == OK) {
				return "OK";
			} else if (result == SESSION_INVALID) {
				return "SESSION_INVALID";
			} else if (result == STUDENT_MEALS_DATA_INVALID) {
				return "STUDENT_MEALS_DATA_INVALID";
			} else if (result == APP_DATA_INVALID) {
				return "APP_DATA_INVALID";
			} else if (result == CREATE_ACCOUNT_ERROR) {
				return "CREATE_ACCOUNT_ERROR";
			} else {
				throw new RuntimeException("Result value is not supported.");
			}
		}
	}
	
	//private static String URL = "http://" + Perferences.SERVER + ":8080/StudentMealsWebService/services/StudentMealsMain?wsdl";
	private static String NAMESPACE = "http://studentmeals.sciget.com";
	
	public StudentMealsService() {
		super(getUrl(), NAMESPACE);
	}
	
	public static String getUrl() {
	    return "http://" + MyPerferences.getInstance().getServer() + ":8080/StudentMealsWebService/services/StudentMealsMain?wsdl";
	}

	public StudentMealsStateData captchaImageUrl() {
		return new StudentMealsStateData(request("captchaImageUrl"));
	}
	
	public Vector<MenuData> allRestaurantsPermanentMenus() {
		Vector<SoapObject> list = requestVector("allRestaurantsPermanentMenus");
		Vector<MenuData> menus = new Vector<MenuData>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				menus.add(new MenuData(list.get(i)));
			}
		}
		return menus;
	}
	
	public Vector<MenuData> allRestaurantsDailyMenus() {
		Vector<SoapObject> list = requestVector("allRestaurantsDailyMenus");
		Vector<MenuData> menus = new Vector<MenuData>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				menus.add(new MenuData(list.get(i)));
			}
		}
		return menus;
	}
	
	public UserData userData(String key) {
		setMethodName("userData");
		prepare();
		addString("key", key);
		return new UserData(request());
	}
	
	public Vector<RestaurantData> restaurants() {
		Vector<SoapObject> list = requestVector("restaurants");
		Vector<RestaurantData> restaurants = new Vector<RestaurantData>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				restaurants.add(new RestaurantData(list.get(i)));
			}
		}
		return restaurants;
	}
	
	public double getSubsidy() {
		SoapPrimitive primitive = requestPrimitive("getSubsidy");
		return new DataPrimitive(primitive).getDouble();
	}
	
	public Vector<HistoryData> history(String key) {
		setMethodName("history");
		prepare();
		addString("key", key);
		Vector<SoapObject> list = requestVector();
		Vector<HistoryData> history = new Vector<HistoryData>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				history.add(new HistoryData(list.get(i)));
			}
		}
		return history;
	}
	
	public int addLoginData(String email, String password, String emailStudentMeals, String passwordStudentMeals, String stateHash, String captchaCode) {
		setMethodName("addLoginData");
		prepare();
		addString("email", email);
		addString("password", password);
		addString("emailStudentMeals", emailStudentMeals);
		addString("passwordStudentMeals", passwordStudentMeals);
		addString("stateHash", stateHash);
		addString("captchaCode", captchaCode);
		SoapPrimitive primitive = requestPrimitive();
		return new DataPrimitive(primitive).getInt();
	}
	
	public int donateSubsidies(String key, String recipientEmail, int number) {
		setMethodName("donateSubsidies");
		prepare();
		addString("key", key);
		addString("recipientEmail", recipientEmail);
		addInt("number", number);
		SoapPrimitive primitive = requestPrimitive();
		return new DataPrimitive(primitive).getInt();
	}
	
	public String getUserKey(String email, String password) {
		setMethodName("getUserKey");
		prepare();
		addString("email", email);
		addString("password", password);
		SoapPrimitive primitive = requestPrimitive();
		return new DataPrimitive(primitive).getString();
	}
	
    public int addComment(String key, int restaurantId, String name, int rate, String comment) {
        setMethodName("addComment");
        prepare();
        addString("key", key);
        addInt("restaurantId", restaurantId);
        addString("name", name);
        addInt("rate", rate);
        addString("comment", comment);
        SoapPrimitive primitive = requestPrimitive();
        return new DataPrimitive(primitive).getInt();
    }
    
    public Vector<CommentData> getComments(int restaurantId) {
        setMethodName("getComments");
        prepare();
        addInt("restaurantId", restaurantId);
        Vector<SoapObject> list = requestVector();
        Vector<CommentData> comments = new Vector<CommentData>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                comments.add(new CommentData(list.get(i)));
            }
        }
        return comments;
    }
	
    public int uploadRestaurantPicture(int restaurantId, File image) {
        try {
            FileInputStream fin = new FileInputStream(image);
            byte[] contents = new byte[(int) image.length()];
            fin.read(contents);
            return uploadRestaurantPicture(restaurantId, contents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FAIL;
    }
    
    public int uploadRestaurantPicture(int restaurantId, byte[] image) {
        setMethodName("uploadRestaurantPicture");
        prepare();
        addInt("restaurantId", restaurantId);
        addBytes("image", image);
        SoapPrimitive primitive = requestPrimitive();
        return new DataPrimitive(primitive).getInt();
    }
    
    public Vector<FavoritedRestaurantData> favoritedRestaurants(String key) {
        setMethodName("favoritedRestaurants");
        prepare();
        addString("key", key);
        Vector<SoapObject> list = requestVector();
        Vector<FavoritedRestaurantData> favorites = new Vector<FavoritedRestaurantData>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                favorites.add(new FavoritedRestaurantData(list.get(i)));
            }
        }
        return favorites;
    }
    
    public int setFavoritedRestaurant(String key, int restaurantId) {
        setMethodName("setFavoritedRestaurant");
        prepare();
        addString("key", key);
        addInt("restaurantId", restaurantId);
        SoapPrimitive primitive = requestPrimitive();
        return new DataPrimitive(primitive).getInt();
    }
    
    public int removeFavoritedRestaurant(String key, int restaurantId) {
        setMethodName("removeFavoritedRestaurant");
        prepare();
        addString("key", key);
        addInt("restaurantId", restaurantId);
        SoapPrimitive primitive = requestPrimitive();
        return new DataPrimitive(primitive).getInt();
    }
    
    public int userId(String key) {
        setMethodName("userId");
        prepare();
        addString("key", key);
        SoapPrimitive primitive = requestPrimitive();
        return new DataPrimitive(primitive).getInt();
    }

    public int uploadRestaurantFile(String key, int restaurantId, String hash, int type, int size, String fileKey) {
        setMethodName("uploadRestaurantFile");
        prepare();
        addString("key", key);
        addInt("restaurantId", restaurantId);
        addString("hash", hash);
        addInt("type", type);
        addInt("size", size);
        addString("fileKey", fileKey);
        SoapPrimitive primitive = requestPrimitive();
        return new DataPrimitive(primitive).getInt();
    }
    
    public Vector<FileData> restaurantFiles(int restaurantId) {
        setMethodName("restaurantFiles");
        prepare();
        addInt("restaurantId", restaurantId);
        Vector<SoapObject> list = requestVector();
        Vector<FileData> files = new Vector<FileData>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                files.add(new FileData(list.get(i)));
            }
        }
        return files;
    }
    
    public String iconsZipFileSha1(String hashes) {
        setMethodName("iconsZipFileSha1");
        prepare();
        addString("hashes", hashes);
        SoapPrimitive primitive = requestPrimitive();
        return new DataPrimitive(primitive).getString();
    }
    
    public Vector<Integer> mostVisitedRestaurants(String key) {
        setMethodName("mostVisitedRestaurants");
        prepare();
        addString("key", key);
        Vector<SoapObject> list = requestVector();
        Vector<Integer> restaurants = new Vector<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                restaurants.add(Data.parseInt(list.get(i)));
            }
        }
        return restaurants;
    }
	
}
