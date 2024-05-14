public class BenhAnThuong extends BenhAn {
    private long phiNamVien;
    public BenhAnThuong(String maBenhAn, String maxBenhNhan, String tenBenhNhan, String ngayNhapVien, String ngayRaVien, String lyDoNhapVien) {
        super(maBenhAn, maxBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
    }


    public void setPhiNamVien(long phiNamVien) {
        this.phiNamVien = phiNamVien;
    }

    public long getPhiNamVien() {
        return phiNamVien;
    }

    @Override
    public String getData() {
        return this.getId() + "," + this.getMaBenhAn() + "," + this.getMaBenhNhan() + "," + this.getTenBenhNhan() + "," + this.getNgayNhapVien() + "," + this.getNgayRaVien() + "," + this.getLyDoNhapVien() + "," + this.getPhiNamVien();
    }

    @Override
    public String toString() {
        return "BenhAnThuong{" +
                super.toString() +
                ", phiNamVien='" + getPhiNamVien() + '\'' +
                '}';
    }
}
