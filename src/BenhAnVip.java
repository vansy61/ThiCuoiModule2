public class BenhAnVip extends BenhAn {
    
    private String goiVip;
    public BenhAnVip(String maBenhAn, String maBenhNhan, String tenBenhNhan, String ngayNhapVien, String ngayRaVien, String lyDoNhapVien) {
        super(maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
    }

    public void setGoiVip(String goiVip) {
        this.goiVip = goiVip;
    }

    public String getGoiVip() {
        return goiVip;
    }

    @Override
    public String getData() {
        return this.getId() + "," + this.getMaBenhAn() + "," + this.getMaBenhNhan() + "," + this.getTenBenhNhan() + "," + this.getNgayNhapVien() + "," + this.getNgayRaVien() + "," + this.getLyDoNhapVien() + "," + this.getGoiVip();
    }
    @Override
    public String toString() {
        return "BenhAnVip{" +
                super.toString() +
                ", goiVip='" + getGoiVip() + '\'' +
                '}';
    }
}
