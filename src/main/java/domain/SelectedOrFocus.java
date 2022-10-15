package domain;

public class SelectedOrFocus {

	Boolean isSelected;
	Boolean cellHasFocus;
	
	
	public SelectedOrFocus(boolean isSelected, boolean cellHasFocus) {
		super();
		this.isSelected=isSelected;
		this.cellHasFocus=cellHasFocus;
	}
	public Boolean getisSelected() {
		return isSelected;
	}
	public void setisSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Boolean getCellHasFocus() {
		return cellHasFocus;
	}
	public void setCellHasFocus(Boolean cellHasFocus) {
		this.cellHasFocus = cellHasFocus;
	}
	
	
}
