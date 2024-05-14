import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

public class QuanLyBenhAn {
    private ArrayList<BenhAn> benhAns = new ArrayList<>();
    // regex ma benh an like "BN-1234"
    private final String REGEX_MA_BENH_AN = "^BA-[0-9]{3}$";
    private final String REGEX_MA_BENH_NHAN = "^BN-[0-9]{3}$";

    QuanLyBenhAn() {
        readFile();
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = Helper.getInt();
            switch (choice) {
                case 1:
                    add();
                    break;
                case 2:
                    delete();
                    break;
                case 3:
                    showList();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Không hợp lệ, vui lòng chọn lại!");
                    break;
            }
        }
    }


    private void add() {
        while (true) {
            showMenuAdd();
            int choice = Helper.getInt();
            switch (choice) {
                case 1:
                    addBenhAn("normal");
                    break;
                case 2:
                    addBenhAn("vip");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Không hợp lệ, vui lòng chọn lại!");
                    break;
            }
        }
    }

    private void addBenhAn(String type) {
        BenhAn benhAn;
        String maBenhAn = "";
        String maBenhNhan = "";
        String tenBenhNhan = "";
        String lyDoNhapVien = "";
        String ngayNhapVien = "";
        String ngayRaVien = "";


        while (!maBenhAn.matches(REGEX_MA_BENH_AN)) {
            System.out.println("Nhập mã bệnh án đúng định dạng (BA-XXX):");
            maBenhAn = Helper.getText();
            try {
                checkExistMaBenhAn(maBenhAn);
            } catch (DuplicateMedicalRecordException e) {
                System.out.println(e.getMessage());
                maBenhAn = "";
            }
        }

        while (!maBenhNhan.matches(REGEX_MA_BENH_NHAN)) {
            System.out.println("Nhập mã bệnh nhân đúng định dạng (BN-XXX):");
            maBenhNhan = Helper.getText();
        }

        while (tenBenhNhan.isEmpty()) {
            System.out.println("Nhập tên bệnh nhân, tối thiểu 1 ký tự:");
            tenBenhNhan = Helper.getText();
        }

        while (lyDoNhapVien.isEmpty()) {
            System.out.println("Nhập lý do nhập viện, tối thiêểu 1 ký tự");
            lyDoNhapVien = Helper.getText();
        }


        while (!validateDate(ngayNhapVien)) {
            System.out.println("Nhập ngày nhập viện đúng định dạng (dd/MM/yyyy):");
            ngayNhapVien = Helper.getText();
            System.out.println(ngayNhapVien);
        }

        while (!campareNgay(ngayNhapVien, ngayRaVien)) {
            System.out.println("Nhập ngày ra viện đúng định dạng (dd/MM/yyyy):");
            ngayRaVien = Helper.getText();
            if(!validateDate(ngayRaVien)) {
                ngayRaVien = "";
            }
        }

        if(type.equals("vip")) {
            String goiVip = "";

            getvip:
            while (true) {
                System.out.println("Chọn gói vip");
                System.out.println("1. VIP I");
                System.out.println("2. VIP II");
                System.out.println("3. VIP III");
                System.out.println("Vui lòng chọn: ");

                int choice = Helper.getInt();
                switch (choice) {
                    case 1:
                        goiVip = "VIP I";
                        break getvip;
                    case 2:
                        goiVip = "VIP II";
                        break getvip;
                    case 3:
                        goiVip = "VIP III";
                        break getvip;
                    default:
                        System.err.println("Không hợp lệ, vui lòng chọn lại!");
                        break;
                }
            }
             benhAn = new BenhAnVip(maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
            ((BenhAnVip) benhAn).setGoiVip(goiVip);
        }else {
            System.out.println("Nhập phí nằm viện:");
            long phiNamVien = Helper.getLong();
            benhAn = new BenhAnThuong(maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
            ((BenhAnThuong) benhAn).setPhiNamVien(phiNamVien);
        }

        benhAns.add(benhAn);
        updateFile();
        System.out.println("Thêm bệnh án thành công!");


    }


    private void checkExistMaBenhAn(String maBenhAn) throws DuplicateMedicalRecordException {
        for(BenhAn benhAn : benhAns) {
            if (benhAn.getMaBenhAn().equals(maBenhAn)) {
                throw new DuplicateMedicalRecordException("Bệnh án đã tồn tại, vui lòng nhập lại!");
            }
        }
    }

    private BenhAn findBenhAnByMaBenhAn(String maBenhAn) {
        for(BenhAn benhAn : benhAns) {
            if (benhAn.getMaBenhAn().equals(maBenhAn)) {
                return benhAn;
            }
        }
        return null;
    }

    private void delete() {
        BenhAn benhAn;
        System.out.println("Vui lòng nhập mã bệnh án:");
        String maBenhAn = Helper.getText();
        benhAn = findBenhAnByMaBenhAn(maBenhAn);
        if(benhAn == null) {
            System.out.println("Không tìm thấy bệnh án này!");
            return;
        }

        while (true) {
            System.out.println("Xác nhận xóa:");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int choice = Helper.getInt();
            if(choice == 1) {
                benhAns.remove(benhAn);
                System.out.println("Xóa thành công!");
                updateFile();
                return;
            }else if(choice == 2) {
                return;
            }
            System.err.println("Không hợp lệ, vui lòng chọn lại!");
        }

    }

    private void showList() {
        if(benhAns.isEmpty()) {
            System.out.println("Chưa có bệnh án nào!");
            return;
        }
        for(BenhAn benhAn : benhAns) {
            System.out.println(benhAn);
        }
    }

    public void showMenu() {
        System.out.println("-- CHƯƠNG TRÌNH QUẢN LÝ BÊNH ÁN --");
        System.out.println("Chọn chức năng theo số (để tiếp tục): ");
        System.out.println("1. Thêm mới");
        System.out.println("2. Xóa");
        System.out.println("3. Xem danh sách bệnh án");
        System.out.println("4. Thoát");
        System.out.println("Chọn chức năng:");
    }

    public void showMenuAdd() {
        System.out.println("Chọn loại bệnh án cần thêm: ");
        System.out.println("1. Bệnh nhân thường");
        System.out.println("2. Bệnh nhân vip");
        System.out.println("3. Quay lại");
        System.out.println("Chọn chức năng:");
    }

    private boolean campareNgay(String startDate, String endDate) {
        try {
            String[] dateArr1 = startDate.split("/");
            String[] dateArr2 = endDate.split("/");
            Date date1 = new Date(Integer.parseInt(dateArr1[2]), Integer.parseInt(dateArr1[1]), Integer.parseInt(dateArr1[0]));
            Date date2 = new Date(Integer.parseInt(dateArr2[2]), Integer.parseInt(dateArr2[1]), Integer.parseInt(dateArr2[0]));
            if(date1.after(date2)) {
                System.out.println("Ngày ra viện phải lớn hơn ngày nhập viện!");
                return false;
            }
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private boolean validateDate(String date) {
        try {
            String[] dateArr = date.split("/");
            new Date(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private void updateFile() {
        try {
            FileWriter fileWriter = new FileWriter("data/medical_records.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(BenhAn benhAn : benhAns) {
                bufferedWriter.write(benhAn.getData());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        try {
            FileReader fileReader = new FileReader("data/medical_records.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                String[] data = line.split(",");
                BenhAn benhAn;
                if(data[7].startsWith("VIP")) {
                    benhAn = new BenhAnVip( data[1], data[2], data[3], data[4], data[5], data[6]);
                    ((BenhAnVip) benhAn).setGoiVip(data[7]);
                }else {
                    benhAn = new BenhAnThuong( data[1], data[2], data[3], data[4], data[5], data[6]);
                    ((BenhAnThuong) benhAn).setPhiNamVien(Long.parseLong(data[7]));
                }
                benhAns.add(benhAn);
            }

        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
