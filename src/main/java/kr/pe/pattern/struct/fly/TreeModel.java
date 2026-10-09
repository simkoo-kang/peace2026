/**
 * TreeModel.java
 */
package kr.pe.pattern.struct.fly;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 모든 나무 인스턴스가 공유할 '모델과 텍스처' 정보 (본질적 상태)
 * 
 * PC  2026. 9. 10. 오후 8:09:56
 * </pre>
 */
public class TreeModel {

	private String mesh; // 3D 모델 데이터
	private String texture; // 텍스처 이미지

	/**
	 * <pre></pre>
	 * 
	 * @param mesh
	 * @param texture
	 */
	public TreeModel(String mesh, String texture) {
		super();
		this.mesh = mesh;
		this.texture = texture;
	}

	/**
	 * <pre>
	 * 외재적 상태(x, y)를 렌더링 시점에 전달받아 동작함
	 * 
	 * PC 2026. 9. 10. 오후 8:11:23
	 * </pre>
	 *
	 * @param x
	 * @param y
	 */
	public void render(int x, int y) {
		LogUtil.debugAll(this, "나무 렌더링: 모델[{}], 위치: ({}, {})", this.mesh, x, y);
	}

	/**
	 * @return the mesh
	 */
	public String getMesh() {
		return this.mesh;
	}

	/**
	 * @return the texture
	 */
	public String getTexture() {
		return this.texture;
	}
}
