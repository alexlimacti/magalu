package com.magalu.desafio.enumerator;

public enum Death {
	
	MOD_UNKNOWN(0),
	MOD_SHOTGUN(1),
	MOD_GAUNTLET(2),
	MOD_MACHINEGUN(3),
	MOD_GRENADE(4),
	MOD_GRENADE_SPLASH(5),
	MOD_ROCKET(6),
	MOD_ROCKET_SPLASH(7),
	MOD_PLASMA(8),
	MOD_PLASMA_SPLASH(9),
	MOD_RAILGUN(10),
	MOD_LIGHTNING(11),
	MOD_BFG(12),
	MOD_BFG_SPLASH(13),
	MOD_WATER(14),
	MOD_SLIME(15),
	MOD_LAVA(16),
	MOD_CRUSH(17),
	MOD_TELEFRAG(18),
	MOD_FALLING(19),
	MOD_SUICIDE(20),
	MOD_TARGET_LASER(21),
	MOD_TRIGGER_HURT(22),
	NOT_FOUND(23);
	
	private int cod;
	
	private Death(int cod) {
		this.cod = cod;
	}

	
	public int getCod() {
		return cod;
	}

	public static Death toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Death x : Death.values()) {
			if (Integer.compare(cod, x.getCod()) == 0) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
