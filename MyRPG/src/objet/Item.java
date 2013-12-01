package objet;

public class Item {

	private int id;
	private String nom;
	private String icon;
	private Type type;
	
	private int force, defense, pointdevie;

	public Item(int id, String nom, String icon, Type type) {
		this.id = id;
		this.nom = nom;
		this.icon = icon;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getIcon() {
		return icon;
	}

	public Type getType() {
		return type;
	}

	public int armement(int force){
		if(this.getType() == Type.Arme){
			this.force += force; 
		}
		
		return this.force;
	}
	
	public int renforcement(int defense){
		if(this.getType() == Type.Armure){
			this.defense += defense;
		}
		
		return this.defense;
	}
	
	public int soin(int pointdevie){
		if(this.getType() == Type.Potion){
			this.pointdevie += pointdevie;
		}
		
		return this.pointdevie;
	}
}
