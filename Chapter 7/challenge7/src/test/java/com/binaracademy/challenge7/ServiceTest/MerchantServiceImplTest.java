package com.binaracademy.challenge7.ServiceTest;

import com.binaracademy.challenge7.Model.Merchant;
import com.binaracademy.challenge7.Repository.MerchantRepository;
import com.binaracademy.challenge7.Service.MerchantServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class MerchantServiceImplTest {

    @InjectMocks
    private MerchantServiceImpl merchantService;

    @Mock
    private MerchantRepository merchantRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddMerchant() {
        Merchant merchant = new Merchant();
        //mengatur repository unutk menyimpan objek
        Mockito.when(merchantRepository.save(any(Merchant.class))).thenReturn(merchant);

        Merchant result = merchantService.addMerchant(merchant);

        assertEquals(merchant, result);
    }

    /* errorrrrrr
    @Test
    public void testUpdateMerchantStatuss() throws Exception {
        Long merchantCode = 123L;
        boolean open = true;

        List<Merchant> existingMerchant = new Merchant();
        existingMerchant.setMerchantCode(merchantCode);

        Mockito.when(merchantRepository.findByMerchantCode(merchantCode)).thenReturn(existingMerchant);
        Mockito.when(merchantRepository.save(existingMerchant)).thenReturn(existingMerchant);

        Merchant result = merchantService.updateMerchantStatus(merchantCode, open);

        assertNotNull(result);
        assertEquals(open, result.isOpen());
    }

    @Test
    public void testUpdateMerchantStatus() throws Exception {
        Merchant existingMerchant = new Merchant();
        existingMerchant.setMerchantCode(1L);
        existingMerchant.setOpen(true);

        Mockito.when(merchantRepository.findByMerchantCode(1L)).thenReturn(Optional.of(existingMerchant));

        Merchant updatedMerchant = merchantService.updateMerchantStatus(1L, false);

        assertFalse(updatedMerchant.isOpen());
    }

     */

    @Test
    public void testUpdateMerchantStatusMerchantNotFound() {
        Long merchantCode = 123L;
        boolean open = true;

        //simulasi findByMerchantCode akan mengembalikan null
        Mockito.when(merchantRepository.findByMerchantCode(merchantCode)).thenReturn(null);

        try {
            merchantService.updateMerchantStatus(merchantCode, open);
            //Memastikan bahwa exception sudah dilemparkan
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            //memastikan exception sesuai dengan yang dharapkan karena code 123 tidak ada
            assertEquals("Merchant with code 123 not found.", e.getMessage());
        }
    }

    @Test
    public void testGetAllMerchants() {
        List<Merchant> merchantList = new ArrayList<>();
        //simulasi mengembalikan semua yang ada di repository ke merchantList
        Mockito.when(merchantRepository.findAll()).thenReturn(merchantList);

        List<Merchant> result = merchantService.getAllMerchants();

        assertEquals(merchantList, result);
    }

    @Test
    public void testGetOpenMerchants() {
        List<Merchant> openMerchantList = new ArrayList<>();
        //simulasi mengembalikan semua yang ada di repository ke openMerchantList
        Mockito.when(merchantRepository.findOpenMerchants()).thenReturn(openMerchantList);

        List<Merchant> result = merchantService.getOpenMerchants();

        assertEquals(openMerchantList, result);
    }
}