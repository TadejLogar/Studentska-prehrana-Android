package com.sciget.studentmeals.client.service;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import com.sciget.studentmeals.client.service.data.CommentData;
import com.sciget.studentmeals.client.service.data.FavoritedRestaurantData;
import com.sciget.studentmeals.client.service.data.HistoryData;
import com.sciget.studentmeals.client.service.data.MenuData;
import com.sciget.studentmeals.client.service.data.RestaurantData;
import com.sciget.studentmeals.client.service.data.StudentMealsStateData;
import com.sciget.studentmeals.client.service.data.UserData;

public class StudentMealsServiceTest {
	private StudentMealsService meals;
	private String key;
	private int userId;

	@Before
	public void setUp() throws Exception {
		meals = new StudentMealsService();
		key = meals.getUserKey("tadej.logar.101@gmail.com", "studentskaprehrana.si");
		assertTrue(key.length() > 0);
		userId = meals.userId(key);
		assertTrue(userId > 0);
		//assertEquals(key, "zgC9iaTh7O3HHJGYkswT");
	}
	
	@Test
	public void testCaptchaImageUrl() {
		StudentMealsStateData state = meals.captchaImageUrl();
		assertNotNull(state);
		assertTrue(state.imageUrl.length() > 0);
	}
	
	@Test
	public void testRestaurants() {
		Vector<RestaurantData> list = meals.restaurants();
		assertNotNull(list);
		assertTrue(list.size() > 10);
		assertTrue(list.get(0).id != list.get(1).id);
	}

	@Test
	public void testRestaurantDailyMenu() {
		//RestaurantData[] list = meals.restaurantDailyMenu();
		//assertTrue(list.length > 0);
	}

	@Test
	public void testAllRestaurantsDailyMenus() {
		Vector<MenuData> list = meals.allRestaurantsDailyMenus();
		assertTrue(list.size() > 0);
	}

	@Test
	public void testAllRestaurantsPermanentMenus() {
		Vector<MenuData> list = meals.allRestaurantsPermanentMenus();
		assertTrue(list.size() > 0);
		for (MenuData menu : list) {
		    if (menu.date != null) {
		        fail("menu.date != null");
		    }
            if (menu.menu == null) {
                fail("menu.menu == null");
            }
		}
	}

	@Test
	public void testAddLoginData() {
		//StudentMealsMain meals = new StudentMealsMain();
		//int result = meals.addLoginData("neo101.matrix@gmail.com", "abcabc", "neo101.matrix@gmail.com", "abcabc", "abc", "tro");
	}

	@Test
	public void testUserData() {
		UserData user = meals.userData(key);
		assertNotNull(user);
		assertTrue(user.getEnrollmentNumber().equals("E1029578"));
	}

	@Test
	public void testGetSubsidy() {
		assertTrue(meals.getSubsidy() == 2.63);
	}

	@Test
	public void testHistory() {
		Vector<HistoryData> history = meals.history(key);
		assertNotNull(history);
		assertTrue(history.size() > 0);
	}
	
	@Test
	public void testDonateSubsidies() {
		meals.donateSubsidies(key, "tadej.logar.101@gmail.com", 5);
	}
	
    @Test
    public void testUploadRestaurantPicture() {
        byte[] image = { 't', 'e', 's', 't', '2' };
        int result = meals.uploadRestaurantPicture(0, image);
        assertTrue(result == StudentMealsService.OK);
    }
    
    @Test
    public void testAddComment() {
        int restaurantId = 1;
        String commentStr = "test comment";
        
        meals.addComment(key, restaurantId, commentStr);
        Vector<CommentData> comments = meals.getComments(restaurantId);
        assertTrue(comments.size() > 0);
        boolean pass = false;
        for (CommentData comment : comments) {
            if (comment.restaurantId == restaurantId && comment.comment.equals(commentStr)) {
                pass = true;
            }
        }
        assertTrue(pass);
    }
    
    @Test
    public void testFavoritedRestaurants() {
        assertTrue(meals.setFavoritedRestaurant(key, 1) == StudentMealsService.OK);
        assertTrue(meals.setFavoritedRestaurant(key, 2) == StudentMealsService.OK);
        assertTrue(meals.removeFavoritedRestaurant(key, 1) == StudentMealsService.OK);
        
        Vector<FavoritedRestaurantData> list = meals.favoritedRestaurants(key);
        assertTrue(list.size() > 0);
        boolean pass = false;
        for (FavoritedRestaurantData favorite : list) {
            if (favorite.getRestaurantId() == 2 && favorite.getUserId() == userId) {
                pass = true;
            }
        }
        assertTrue(pass);
    }

}
