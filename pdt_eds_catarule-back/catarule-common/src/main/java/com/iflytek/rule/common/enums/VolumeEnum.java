package com.iflytek.rule.common.enums;

/** 正卷/副卷枚举类 <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * 
 * @autho dgyu
 * @time 2021年11月1日 下午3:14:02 */
public enum VolumeEnum {

	ZHENGJUAN(1, "正卷"), //FUJUAN(0, "副卷")
	;

	private int	   volumeType;
	private String volumeName;

	private VolumeEnum(int volumeType, String volumeName) {
		this.volumeType = volumeType;
		this.volumeName = volumeName;
	}

	public static VolumeEnum getVolumeType(String volumeName) {
		for (VolumeEnum mapping : VolumeEnum.values()) {
			if (mapping.getVolumeName().equals(volumeName)) {
				return mapping;
			}
		}
		return null;
	}

	public static VolumeEnum getVolumeType(int volumeType) {
		for (VolumeEnum mapping : VolumeEnum.values()) {
			if (mapping.getVolumeType() == volumeType) {
				return mapping;
			}
		}
		return null;
	}

	public int getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(int volumeType) {
		this.volumeType = volumeType;
	}

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
}
