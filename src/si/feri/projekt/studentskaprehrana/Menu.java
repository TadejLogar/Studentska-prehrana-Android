package si.feri.projekt.studentskaprehrana;

public class Menu {
	private long id;
    private String restaurantHash;
    private String contents;
    private boolean vegetarian;
    private String date;
    
    private String food1;
    private String food2;

    public Menu(long id, String restaurantHash, String contents, boolean vegetarian) {
    	this.id = id;
        this.restaurantHash = restaurantHash;
        this.contents = contents;
        this.vegetarian = vegetarian;
        setFood12();
    }
    
    public Menu(long id, String restaurantHash, String contents, int vegetarian) {
    	this.id = id;
        this.restaurantHash = restaurantHash;
        this.contents = contents;
        if (vegetarian == 1) {
        	this.vegetarian = true;
        }
        setFood12();
    }
    
    private void setFood12() {
        int p = contents.indexOf("|");
    	this.food1 = "";
    	this.food2 = "";
        if (p != -1) {
        	int p2 = contents.indexOf("|", p + 1);
        	if (p2 != -1) {
        		p = p2;
        	}
    		this.food1 = contents.substring(0, p).replace('|', ',');
    		this.food2 = contents.substring(p + 1).replace('|', ',');
        }
    }
    
    public Menu() {
    }

	public String getContents() {
        return contents;
    }

    public String getDate() {
        return date;
    }

    public String getRestaurantHash() {
        return restaurantHash;
    }

    public int getVegetarian() {
        if (vegetarian) {
        	return 1;
        } else {
        	return 0;
        }
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRestaurantHash(String restaurantHash) {
        this.restaurantHash = restaurantHash;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setVegetarian(String vegetarian) {
        if (vegetarian.equals("true")) {
            this.vegetarian = true;
        } else {
            this.vegetarian = false;
        }
    }

	public String getFood1() {
		return food1;
	}

	public String getFood2() {
		return food2;
	}

}