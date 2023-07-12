package com.io.minesweeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseHelperTests {

    private DataBaseHelper dbHelper;

    @Before
    public void setup() {
        // Use the context of the app under test.
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DataBaseHelper.USER_TABLE, DataBaseHelper.COLUMN_ID + " IS NOT NULL", null);
        db.close();
    }

    @After
    public void cleanup() {
        dbHelper = null;
    }

    UserModel addUser(UserModel user) {
        if (!dbHelper.addOne(user)){
            return null;
        }
        return dbHelper.getOneByName(user.getName());
    }

    @Test
    public void testAddOne() {
        UserModel user = new UserModel(1, "John", 100, 200, 300);
        assertNotNull(addUser(user));
        assertNull(addUser(user)); // Adding the same user again should return false
    }

    @Test
    public void testDeleteOne() {
        UserModel user = new UserModel(1, "John", 100, 200, 300);
        UserModel inserted = addUser(user);
        assertNotNull(inserted);
        dbHelper.deleteOne(inserted.getId());

        UserModel retrievedUser = dbHelper.getOneById(inserted.getId());
        assertNull(retrievedUser);
    }

    @Test
    public void testGetOneByName() {
        UserModel user = new UserModel(1, "John", 100, 200, 300);
        UserModel inserted = addUser(user);
        assertNotNull(inserted);

        UserModel retrievedUser = dbHelper.getOneByName(inserted.getName());
        assertNotNull(retrievedUser);
        assertEquals(inserted.getName(), retrievedUser.getName());
    }

    @Test
    public void testGetOneById() {
        UserModel user = new UserModel(1, "John", 100, 200, 300);
        UserModel inserted = addUser(user);
        assertNotNull(inserted);

        UserModel retrievedUser = dbHelper.getOneById(inserted.getId());
        assertNotNull(retrievedUser);
        assertEquals(inserted.getId(), retrievedUser.getId());
    }

    @Test
    public void testGetEveryone() {
        UserModel user1 = new UserModel(1, "John", 100, 200, 300);
        UserModel user2 = new UserModel(2, "Jane", 150, 250, 350);
        dbHelper.addOne(user1);
        dbHelper.addOne(user2);
        List<UserModel> userList = dbHelper.getEveryone();
        assertNotNull(userList);
        assertEquals(2, userList.size());
    }

    @Test
    public void testGetEveryoneSortedByTime() {
        UserModel user1 = new UserModel(1, "John", 100, 200, 300);
        UserModel user2 = new UserModel(2, "Jane", 150, 250, 350);
        dbHelper.addOne(user1);
        dbHelper.addOne(user2);
        List<UserModel> userList = dbHelper.getEveryoneSortedByTime(DataBaseHelper.COLUMN_USER_HARD_SCORE);
        assertNotNull(userList);
        assertEquals(2, userList.size());
        // Verify that the users are sorted by hard score in ascending order
        assertTrue(userList.get(0).getHardScore() <= userList.get(1).getHardScore());
    }

    @Test
    public void testUpdateTime() {
        UserModel user = new UserModel(1, "John", 100, 200, 300);
        UserModel inserted = addUser(user);
        assertNotNull(inserted);

        assertTrue(dbHelper.updateTime(inserted.getId(), 50, DataBaseHelper.COLUMN_USER_HARD_SCORE));
        assertFalse(dbHelper.updateTime(inserted.getId(), 150, DataBaseHelper.COLUMN_USER_HARD_SCORE));
    }
}
