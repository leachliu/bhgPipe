package com.bhg.pipeServer.fileTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelRegion {

	class RegionPoint {
		int x;
		int y;

		public RegionPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

	}

	class RegionInfo {
		private int firstColumn;
		private int firstLine;
		private int lastColumn;
		private int lastLine;
		private List<RegionPoint> points;
		private int regionId;
		String value = null;

		// xlsx专用
		public RegionInfo(String regionString, int regionId) {
			this.regionId = regionId;
			String[] grids = regionString.split(":");

			String firstGrid = grids[0];
			String ColumnName = getColumn(firstGrid);
			this.firstColumn = nameToColumn(ColumnName);
			this.firstLine = Integer.parseInt(firstGrid.substring(ColumnName.length())) - 1;

			String lastGrid = grids[1];
			ColumnName = getColumn(lastGrid);
			this.lastColumn = nameToColumn(ColumnName);
			this.lastLine = Integer.parseInt(lastGrid.substring(ColumnName.length())) - 1;
		}

		// xls专用
		public RegionInfo(CellRangeAddress cellRangeAddress, int regionId) {
			this.regionId = regionId;

			this.firstColumn = cellRangeAddress.getFirstColumn();
			this.firstLine = cellRangeAddress.getFirstRow();

			this.lastColumn = cellRangeAddress.getLastColumn();
			this.lastLine = cellRangeAddress.getLastRow();
		}

		private RegionPoint dataGrid;

		/**
		 * 区域数据的存储位置
		 * 
		 * @return
		 */
		public RegionPoint getDataGrid() {
			if (null == dataGrid) {
				dataGrid = new RegionPoint(firstLine, firstColumn);
			}
			return dataGrid;
		}

		public List<RegionPoint> getPoints() {
			if (this.points == null) {
				this.points = new ArrayList<RegionPoint>();
				for (int i = firstLine; i <= lastLine; i++) {
					for (int j = firstColumn; j <= lastColumn; j++) {
						this.points.add(new RegionPoint(i, j));
					}
				}
			}
			return this.points;
		}

		public int getRegionId() {
			return this.regionId;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		private int nameToColumn(String name) {
			int column = -1;
			for (int i = 0; i < name.length(); ++i) {
				int c = name.charAt(i);
				column = (column + 1) * 26 + c - 'A';
			}
			return column;
		}

		private String getColumn(String gridName) {
			int firstDigit = 0;
			for (int c = 0; c < gridName.length(); ++c) {
				if (Character.isDigit(gridName.charAt(c))) {
					firstDigit = c;
					break;
				}
			}
			return gridName.substring(0, firstDigit);
		}

	}

	List<RegionInfo> regions;
	HashMap<String, Integer> gridsRegion;

	public ExcelRegion(String[] regionsStrs) {
		this.regions = new ArrayList<RegionInfo>();
		for (int i = 0; i < regionsStrs.length; i++) {
			RegionInfo region = new RegionInfo(regionsStrs[i], i);
			this.regions.add(region);
		}

		gridsRegion = new HashMap<String, Integer>();
		for (RegionInfo r : this.regions) {
			for (RegionPoint p : r.getPoints()) {
				this.gridsRegion.put(p.getX() + "_" + p.getY(), r.getRegionId());
			}
		}
	}

	public ExcelRegion(CellRangeAddress[] mergeCells) {
		this.regions = new ArrayList<RegionInfo>();
		for (int i = 0; i < mergeCells.length; i++) {
			RegionInfo region = new RegionInfo(mergeCells[i], i);
			this.regions.add(region);
		}

		gridsRegion = new HashMap<String, Integer>();
		for (RegionInfo r : this.regions) {
			for (RegionPoint p : r.getPoints()) {
				this.gridsRegion.put(p.getX() + "_" + p.getY(), r.getRegionId());
			}
		}
	}

	public String getFill(int regionId) {
		return this.regions.get(regionId).getValue();
	}

	public void setFill(int regionId, String value) {
		this.regions.get(regionId).setValue(value);
	}

	public Integer getRegionId(int line, int column) {
		String key = (line) + "_" + column;
		return this.gridsRegion.get(key);
	}

	public void print() {
		System.out.println("gridsRegion.size():" + gridsRegion.size());
		for (Map.Entry<String, Integer> entry : gridsRegion.entrySet()) {
			if (entry.getKey().startsWith("0_") || entry.getKey().startsWith("1_") || entry.getKey().startsWith("2_")) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

		}
	}

}
