package castle;

public class Room {
    private String description;
    private String icon;
    

	private Room northExit=null;
    private Room southExit=null;
    private Room eastExit=null;
    private Room westExit=null;

    public Room(String description) 
    {
        this.description = description;
    }

    public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Room getExit(String direction){
		if(direction.equals("north")){
			return getNorthExit();
		}else if(direction.equals("east")){
			return getEastExit();
		}else if(direction.equals("south")){
			return getSouthExit();
		}else if(direction.equals("west")){
			return getWestExit();
		}
		return null;
	}
    
    public Room setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
        return this;
    }

    public void setDescription(String description) {
		this.description = description;
	}

	public void setNorthExit(Room northExit) {
		this.northExit = northExit;
	}

	public void setSouthExit(Room southExit) {
		this.southExit = southExit;
	}

	public void setEastExit(Room eastExit) {
		this.eastExit = eastExit;
	}

	public void setWestExit(Room westExit) {
		this.westExit = westExit;
	}

	public String getDescription() {
		return description;
	}

	public Room getNorthExit() {
		return northExit;
	}


	public Room getSouthExit() {
		return southExit;
	}


	public Room getEastExit() {
		return eastExit;
	}


	public Room getWestExit() {
		return westExit;
	}


	@Override
    public String toString()
    {
        return description;
    }
}
