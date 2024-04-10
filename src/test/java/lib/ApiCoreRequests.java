package lib;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ApiCoreRequests extends BaseCaseTest{

    @Step("Creation user with incorrect email")
    @Test
    public void testCreateUserWithIncorrectEmail () {

        String email = "vinkotovexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    static Stream<Arguments> userData() {

        return Stream.of(
                Arguments.of("","25369", "Dmitriy_D","Dmitriy", "Ivanov"),
                Arguments.of("ivanov_123@yandex.ru", "", "Ivan_I", "Ivanov", "Ivan"),
                Arguments.of("petrov_234@mail.ru", "8521", "", "Petrov", "Petr"),
                Arguments.of("semenov_147@mail.ru", "74125", "Semen_F", "", "Urievich"),
                Arguments.of("gonchravov_41@example.ru", "749658", "Gonhar_9658", "Concharov", "")
        );
    }

    @Step("Creation users with incorrect date - email or password or name")
    @ParameterizedTest
    @MethodSource("userData")
    public void testNegativeCreateUser(String userEmail, String userPassword, String userName, String firstName,  String lastName) {

            Map<String, String> userData = new HashMap<>();
            userData.put("email", userEmail);
            userData.put("password", userPassword);
            userData.put("username", userName);
            userData.put("firstName", firstName);
            userData.put("lastName", lastName);

            Response responseCreateAuth = RestAssured
                    .given()
                    .body(userData)
                    .post("https://playground.learnqa.ru/api/user/")
                    .andReturn();

            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertJsonHasNotField(responseCreateAuth, responseCreateAuth.asString());
        }

    @Step("Creation user with short name")
    @Test
    public void testCreateUserWithShortName() {

        String firstName = "d";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", firstName);
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, responseCreateAuth.asString());
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    @Step("Creation user with long name")
    @Test
    public void testCreateUserWithLongName() {

        String firstName = "pGphTvWIzAWRoNXZHvsppERSwGSxMDUTUinnFoawHdkEiOSxqotmXSqGexbYMbIOhTYrrBLvdazXfFvxtGKMLLxiUKskNwrZETMkYeszXJPZMFwKqPSBKQBQuMtbsBvhUiyrQyVmVWGyXoeotBTlWceJdXwlAzuKfDnRFabNRnzuTcVHzuMkxathcbIDHHhVCrkTpIfoyzUhgbYieEsfdJLiHNiTZGENVLjbDBYZSbEXsZuXdEIeQWMqRVP\n" +
                "cOAPujUHUVhMHrYHzrVLDuquxulkbCxLOGvhQPrjtyUQgdonmLswwfnrsyIHBwexKwOzsasoAGZwuPmDaOHANkHTEZJpgbzYHmuUnseDZtmrIrhGqUJjTNEGBIcBjVRMJSKSqklvrvhvTHLnkNQtvQshAxyPqeALfcyVgVzMighMHkdOuxSYtRiaDcKZoFraPpGzQKOecENNqrZAUNCQTzlrPVhQBfvkujBbKakdJommHYdvodEmtVkDstE\n" +
                "uADjhTOCloDQBMueuOquqBHmVDaViCjeXwQWbwHGnGesPMftUUPtWNdFgvzKEBStQTnQTMbrRTduwoUDECOotPKbOrqUMCkhNQcnzhcOzswPZjIXaTNPwQcgwIiUfSfCIjblSLnaSocUkWBEfenaCPUMWXaHpDPwQHWwJIPXEWoGTGRsbYrSyIbXXsRUldPgSuOGgeKjkkvyCPDjrImfjkBFYGdvzMEGOzkVmLAyjOvLAtuiDuTHMlnLLQT\n" +
                "WNGYqJwwoILNztMAawMqLMFwRXQMXevjtnPkjTAMvzrBCQzahSJRNShCknszjvZctzOyzWlMCeyJzlMsvGFiSQufTSOEBCXLnmyZuJhKsLYAkVaYYpglnHMWEyQwzOAbAmRhdUbRivbCtxemPhzGeHnDHhOmNbUdjXppjTSmizicpXnwLnDzqSmElPdZPySfKjANNxAbtBeQnItQddlGKKyDfSRLCkFGFqMRUCaoSCTCGJxjnugdYuGSEjH\n" +
                "ufNDrlNksGNPkEGUQheszWWlhGQvXEKHfdyFAbbVAOKAzOIApjrsBNsxSanCpKUENpjbDwmHvduPeOTwoFzASeTlXjJgeBFAOlSpGxEuPmWTHcUvsOZvHDkQIYZqGvCEleAXEZcJczaWRgmfIEGGjAuOQxAHpWOBsUwzGgyZYNaxUQWyGKdycbtSdaEDjyMkbFXFxtXXACkmFtgrxigKZuQfNUqLYDJjzwBIybOpoOoOsGegovVcorQisqB\n" +
                "BMHRRaxrvXAkvlPYxTANepvIafbSmpAqTnCUHrjtrSziKfQOIqtWvkQmfbYUYgVMLnLmFHhjMzhpEmgAdmwGKpOsYiOfoGjpCCwqXPtQJNQsJJoIAHSZpYrYcABplwflUCFWRvRdtdgjmDdXaxnVNDxOwYMtIXFJliVFlvrMXQSyzCDyczuPxmjYUhcSbNBUrtIFjQNubKgaunRrrKFhFqRYXIUUFKdumgkLXdTuJSChUpWmMiwXYWzJzEQ\n" +
                "DJFyMabEakPfFdAUeZpyeHUsoSacBZipGQcxFYAytriwtZbJTKYlSHNGRKmyWwQaYinePUxBNPSMrhNqlkxeOBrAQyTNGmfmLPlhAknFPvwMyfICgTiFSyzmaLqFxemrPONzSpKGksLoNdXgRhBFoqSZnGOryyMIwEyVpPnIaZSplGjAlREEqCZwWYnqshJnwcMSdAQbXuDhhjMZNuDDfbakPCCkfNjPMQQBUMwsWZayFygCzeUmFdzRgaA\n" +
                "fWcmAzdeVoKRzRBQpLoNWgiTdYzBGYBtiMomGfXqFFYjkCzvLjFIqMAcVnWWAVZSZERbkCFKHbLzmdHBpoNQSDQlQwCuOzKYztFOnmgwtEOBNsulLgCrPcoKCkXyqNUfvjMfuFDzibNrerQmpyMfBmJNbWoLhNyOVISzFEBjpnsEpZSrubaSIkSbUNXQKnNkmnsBdqceuCyxMeVfhvWIxgKuWMRFkNpuZlyuSxMuOkIZtniUyBOnNTmtruG\n" +
                "XqEBCybsCXBIlAbZzDxNfiApilFVnJdRtQkTsSMxjDJwKPGhuJJogToJgaICionDxEWbHeMeaeFlUdcVItZeVTrPEtoFMsdtLZQTvxoHHvxcsuHoqGDihTwtxWcXxOUVNviHxAfkUKaQLdOSSgajEkqbCBjSotcgZfxclwgMEiDYVSrZMNoKFvrDNGOuwXTgvPTycXdqOmgFcrTjFLjTkhRGPNkmNbIaEfXBjdunktgEQRGnyxtAhDFVoOd\n" +
                "vrgjvcvGMBaNlOxtvJzrEkRUDjOiqRFBjOPoFuCUbpRfPhgAGPnskdtSRlUPAoHpCAXAFDLrQXNrTmjuXXUAQFkgSwKPdVGOGqzznVCYdAMbqLCodFCalfkfGZpAxthzVVLtgvPWDILeZAdkMwOsfYxRawJEGOdDyrJzyRJfXjYZaLyBafhUvlXozBbVdamAdIRMdxAxmrsFzcYCzrVaKjZvIckMLKagLOpXbgQyBsMhjIvydBqJdlMsJuT\n" +
                "FRCXOUDvpACeefjkwjDGyprQfDUrECccuZSMxiPrbQVMYIvcgDrqAbddOfSnrDzwHibNGTxdoyXvObohSHvSbcwVQfHssJmCjjRxlWBfxcTCFhBBWBeKStXMNEJgROpPMCgUFYPQwLVEqkAvgQTMhuVMislPwRbQySXQulinpQOgohvWrvuyQqXQuwGzVUeiFauTawZFqpNIhcvSWsPXyOZYcbfRLPRurwQVsvpSHnWkMCKgZJxEZmdgHil\n" +
                "hAwXJTAcRCQaIffJhKVJUkpbLjDQPgDreVGWGDCbqhSZMYtEzxTDivKivpsgnFZolyFTuheWaOWjGeEcZoKFGkcSDhiEkAvQyytSUYwzDBrDTEiLiMAzxhAURvbtVjjMlfIGyxqRlWwmwDVpryRFWJixedgrogaaCjBjqaijrdFVuWLjXMHBmnsUDsyzWSEaGCJFyzWZDPGJsmcfLfjvCXNUwCCqWtOsHmUbEiLlbzzjgfogNqtgAuhPVXi\n" +
                "xreycxCGDCLraJbYHMlBHOZIRplhWEqLcXZBVPAErabZaXQQMqFldTipkrODrfTaCeZgAVFXmSGSReSDMshdFigowRhiHGAGFlSFSLmiyuLhasLiVFKMohllHECHFcRfpcKLLvTRWatZjckyeConJmtTTSeepcCwEIfPYlmTqMoMpRHycySiDjyNvkGqothZvYOHpIKQieTvHpClbkFDcywXWVmKDrOcQAYaGHPTvUSPjBycxKZxqCioonv\n" +
                "yUhESyuRgJaziJAnudHAyJgLuPVVbFDBOCrAIMfzMlHZfZTDeGNOhbsKXeZRZaFEFWTUkxOCNXdoufQLwYNYMbVZlvHzlmVGrhmYDsPmVXwVcLQkhcebGMehDGNKHQLPjSENfoyJunoniEmNlQMFbEoZKtGTObPBTvsxxtpUZshlICNKwiqQIoMZKLJuoQqLOhPVijqLcEhUhljXAsdxCRweMwVErQqsXAVUcAzNvnckqqCkNLhIjgZzBvl\n" +
                "wUSOpIIyLFkkGgsIkrqaPnbVsETqerWgcdXscehMVwIjPaWZZomVzkwGZyrEVNBUABDGoprnOivBrxMxeSIWABNdNlNzxOafSPLQsbTEAGKfLRVxrxRFtwvZQVLOhNDpmaidtdsqxqrDTyaXLGNkqZKjDJvdawOOTxDmENGDeaQaVNdOwOfSkWtaOyhmzSfvAqTucifblsjpdPosbFchgTVcqfGKbXADfLyyucWPeXiRhEqQBydKdrUjUUl\n" +
                "HghXwdCLUCDykWoBMNSseWkiaHRDRYuxVxpWZuOxGlUxWHvjFvnpzmkNWccNDzOmnwGPeXTBPeCnZVItMwmMLdGrxcNnOOAWLejpVgtMOjfpLZvCaPwrsYGfJfCxtgXdzchGHwGGqnEeDODdekGmGfCvMYQiRaKDoEnnIisOgCgdwNlCkshLFMzMloRkhpsptUbmsUZXYFCMiIvOckhHYgCwirucZGoQeRLaMwKkbpdOZUpwgbYIHmwDCxc\n" +
                "GQIaGFHXhstjgPnMjkqRHfjglgBBCpmgziHCJVRFLbaVFpsjIzqfTzhIqdFkqPrlAPlVHWNENxDASbWskcCohDMuUwrHDwjdGSUimqdfhQXqjalwOGQFBjPeuTaSNtsxpfFGFPQxyLzXMZyiSXPNlCyfKIgeTXjwHhAVyJKPrMejLOPSabIEypwNMqWorAbycrDeFyhdeVwIcgumBLEPjwEfusEwoxevITvFNpPyQemuGEklDWbfGZRaDgP\n" +
                "IqCCLJpLHzGSPvfaZeSkdfwnhFQJxKQEVSMvMUlGbGPyaxJAZtLXgQaEREQoMPNGNRlAlXpzmWBhDpWoIOnLJUbBiVAaksVTKmZcdRTOxykVICdsayXZtTFnEeMKEbaYpqhCXRYRmDzEerLvedZbpSVDgalkekeLhmVVFcwfDzIgJatiFvEQbUkusfkOsndZtUTcxgAffQsxDlySftEgVxEqTJDlOfVibSLwanJONzzIXVhAUIcTFHIATCc\n" +
                "EEfxmCehRYblSgaZZPiaCxZxylGrGcWhkNvJrGMxqAuMVqTNIhjWCuHeaktBvEyoxYTWMoGMFUKEvEvOgshqxfnnQOCeYJvmFlIUgNQQAxIozoNsSRWEGBSVPQmJRebmkzZpuvhpVUQvIwcTtxdcMlCVVZrsMFlgPoEKuzoQOXjzBMcQaNUmSTRNsRQUWuAHGziXPIxQkdGfDUydWtEDlQygZlkDVUHvZgBWsTpaWdbpHkIexnvtGEJgJjk\n" +
                "gEFDUKYRaOWfQnNIGuCQkyQlwjRAOyMqZCKpaRcXmrrEgVGoMzhTOlDUvlfxhibDIUmceAGulPKzLkknRSNnsjiNmvMtZhoZcTptlpBaxnfNxhqOXStGzvxVItRvFOfXOvlcUihXsrgDSObQLnPJJkwPiVEiFYzPfFKrhyabIDAhyeIbKBreDXvHGtjGbxkENIJrwjpFrgxmQsQXrmIPGEUujbgfnawxhpOJhVbFrYexgLzOILzbqNcuhRM\n" +
                "hwOxDYNTgKfeGLWqTHypolhljtIoSawFyvDMeeIiPonLIOdRvkYCdrdlUItwDglcODIikUuFTJCjQjEEEwXirkauqECmCBoCAzWLihcQsSdPfzRrFMWmXyuvjBmJhNpqVpeHiNPmPXvlnVGApQVvBAheBtQnybNTJxlZyToCczRtieDKaSNiLrtBHRhTzVUTHYPORUFyjQgLwTRElimMKjmbSqdRPsNPvCuweiMbHtnMjBQSAyLvSdpYujm\n" +
                "JDmMfGYbQrYdBakKEUfZkEziQtHUAQFYuQLyELBawFsMKwEgVMqadEdKEmfBNkfzOjlZeHPIcVBTCrfCACapgzpPzkRZMyiHceXFiKmLvYnkkwCFgcAbKOtOXNrjbzMWMYerRSDmCTcubUEmqEsUxrntBSAMuhXSgmNjpjKwsGlxnxwFgVZvmVbXaRBdMvPVfZcSbTNyFQzPrNULdcmrUtndxSOCGOQLAemqcpPHDNuzLFgjNKVMNxrsSOZ\n" +
                "PbaeCdoMikjUThpABjrOqFyvlRWKHbRAZDyCwUlAcZLhnwiwjrxpfpkVkVHqMuAnQySRngfZXNjuezxwmdcJJXFoJutbfmwhIprjeEPkIYnjSCeLUkeyZjAglerbhqYXOJXIdDKuaGrRRePihWPveMBUcekwCRxjiBjckCBqIdEAnHyYjqBQkYLomcqItGVtszYzmtsMKOilXUEYMiEXBHviXaUSvNpfKHQCyuSbfiaNIhKewIHiIMShfdk\n" +
                "LKmIHaqLTDzMISLLlYQMvfJSqbPROedhViRRGuyfxanxAiiODuGKubQJbIILgVrxVxAseyZJYoTqShoyeMOWxMDFxVfaSVxVnzTTGzIocUwDeHzFhMYtYxefTutBafFmkPUPXjQOjNOcxSPKBbnSavvlcLYKdmCUFhkNetleDCcLVuazYjQcTFnAloiNeiwiJxpJaXYeWBFNhopxbunocDTorDDFjFnkdNiudwJbuzLCqEVZUGujbPMLUJO\n" +
                "fbTgOhvkThVMEUYwvfltMRMluAUhzJKsmZXXilpGSaEXvuETnHKwyopRfUCRdzddUmQyQHyvRrAozUwweuvEicsNigvgdpSfWtaASgpptBnpJftgjcqpjCuoSBJseJIhXRHzCAJzyWdqKveUYxLTGPqUOdbYybtWEMlSUwfCfCfCpIVUKaCEIgPzhDRtTadFFVPdtykGYHUhORggalHGqoAQeQffqsdeuASXPcvebnUGZTlEVqrFgYYiBbQ\n" +
                "RztXiFJMQjzXNwDciDgyAaerVZFJAJckSuOmQAUhdkmigFDdIonReEforENrWPDLoSWvkbtmbeHNSbWGbTKQgjYmceJHCtErQMVqQAMaQKhQtYVGgOWWMGRbGlwtOtOHuvpLdWmUEuGMmKVfDkiMlhsAGaWbXyxfCYsaRUnSnkPtKHsGMhYLXSXuonLPmSzEmpYvBAfVIVLESQWRncGYFklGguTJvnnJnNtlLxENForvkeYPgBmIdDHhaXR\n" +
                "iNEfVQnhYIZqeUdvnhbcvSvcGfoERWbRIscsTwjAbvbziGBLoGQnRRsRBGPZSClvkmIWGrCuMoJlCFyhiUtXQLrSqoAedrNnpfBgyrkAkpeZYWejfrcvIdWDSnZQQbFuMrqVzDLZsjiYYsTodlUqgJERQlLiKulVTmIsHwhMQDhIoVTFSczkDKpOcGqIPDUhaNNBmGMVUKGqMawXpghRYeRQXlUGJoohVDfmjSlhpRitcZiGyuYtKUOkxjC\n" +
                "VLvUOdxxAmidHoPPaAFCKuLCWQGFIKOsTYeHepPKhzxljkTHLHpHARrhcMNbUlnvLsLkjrFACPStHZkwqlFYaHEMBIRASQzTQhmRnxOQQzCApNHiCZvpKrYeYXpbxfBFKnOqPGUcHmKlbnqRmFuTWSJMZMnzkteAdLzfuCVokrUwvLoYnuBwuYRWEqsmGlIHDJEBNEbxtsUqWOtFAmxuWifsxDHeYlRwUsrcozlWWvMfHzrdkzjJqDzLqXa\n" +
                "IihovEWdTRFTtZqFHWmoTlsgbXiWlZUvlNPBRajhFDOVFxIKPcWYTFDDreCJOQSLIdrNlAlwcaLrpvrRSkkggvnobHROvXFTnVoUXdLXQRMamkGMiAqMjsVCxGZQKzXxkXFyrrAlwOJhXsuaKVgkvWnPXsXsTLVRogdjwYmPMfQnlGwQnzqNhlhDZTBSVTZRnyMVAGXpGykjyeGqyVchxkquPZztABwVDDtDqLOCKzsIdCRsvnnoCPjhFiW\n" +
                "FvAXBNqLFxJDVMTfIvEbjgwXHaProWTlLdLRBFMqsaLFGCGrgefoENMCtmtdUGazjljDHXbzdEDdLjpmMojrNAKeQliNHnuUsvquUxSLDxabWpfcYAOYMDdQbasGReNxRWwcXGUHCxtjHSGrfnhhVdprRQvMVDrjnSdQZXntKOGqLIunhuXlDiEwjQgvifwIIptyiGINfsTEpiSVdPlxRNAiZbzADBWmtKetDawGdYUuunIdWwsJYfuIxQo\n" +
                "WseQtZOTaFomEOeWWjgUhlBGfaLpcTYlaxTAswqhZXUFxZGZQctuyVonUgurqnxqpZgxKInHVuIkXFZUmGcArWoTwxHKZUdfhKtPYHSOzyBnTzodtyUdhEebdpwxpCktNxhrwzzDzvYJergjCCXZFZnPXnjrIEBApOgRAfWypBUjXNDTSjPtHfhZaUNBkwuQawsWACYkkEQUeJeyddHxrUVtdxFVQLNRhGoPUiLBQssSlHrlzFYwYZIUkgs\n" +
                "LXboMCYKxjNkCvEhJlXNyZaYoYnQvkKeazLauKmDUEOBqLzcqFrkSgDFXlcaRsNxtELToyVDrxHXyIrOlPZfpTOdCgTHGsHODmzMiYQlymktgSKLdEmQASNdolotYyKseJQyrzpVyAuZIJQJdnrqwYVCxYUHWYFsqzznbbvsSkMfYmUYyqqEItvukWRJGhlmlCAYrsQQDHeXPkxDnLbtcsCEoKFRtQdWwwxizoftGDqTwLrwhWLyedqEocp\n" +
                "avvrSFGcMviDbMrELdbWppQzUOUAfqkbWchLhkXQSyTsKqSyjRajpaVPXzGFEJaHPdhXgWIJZCHzhCIHZZiXnqKNMlbuUxBqNKxNtdjKkWNmojGeAdwHIHCRgIPFSMjnahpXVKlcPBCJZmWljreYXEJymmoDWKdUPsMyBqZpgaHeVTJcBMowwyLagXFEdISOFWDiptbKbzhsjLsntKeBTxzGCEPMzrzaQGvldiJDoVHMiQOxWkmIbXrtRLP\n" +
                "rsAFAmUVzlzrAVSuJgukyLRpCTvJzQQvrFpYLctWskkZvEivjUDOfrBrRacBVHsjFcsYlTxCfrjjEBTcsXtvSvQgmOsShLZfsKQbUoRjVBkhJtejgRJAJoHrECnLWCXOrmdRVJHHrAQtjIulqMkJealOzHxeTSMkedWdzTMIvPZAUyJrnyDqGVZiWvTguaodCwpQCeJtmLdlkQJdvmvYbLjIfsXrOCDQUirGqEvFxVUrLksoehbPFqKOCkx\n" +
                "JGphwBvpHIksCkABaxHusZMFvcmzWlTqNJXdYOxePaMfCSnXOjhGEswlfnbrsISoUGucfMJhLmyCzjmgzTWDEcRHQWBqryZohxoKPsPlmqArgizfFmksACjfTOWgXumtzmNJYXgeefcrIBJupCRbeSDJeFFMrimkROVMUSMcQlZLModlbbQsdaoYkPOdLtFhipeRtsdPjRHvlVkkxqPOiWuywuiiqDWTDhjyZeTPyXbduIvZRKueuKJSxGj\n" +
                "hIrBBSwoRmDsvYBzwngoiVSnrjQQpzXnsDTbVAblwgBbFCGtjRWYTEvHcPSbEGVOikYPpXFmQpjkHKOQwcSVEXRDnCgatDmXQTYRRqMsKoHFgXuUhqUAcKTrNQfGMwmMykjeWLoTuQkgEjbHPJcLrIJFTEBzfiCkAtCyomigJEJAqiuzdQfvTLASoWYMXvxvSsBfSIDwMQIZvePwAUESHvLnioSHxeMxDxSNQERXKNwvyyjYsuMRgKKFtpl\n" +
                "hAdjVshcwYPcWXhBwjuFYgFHUBMeGJdVhfqRHztHUKcYLzCsXmkedueLBophuAGJIbwYwGkGCsQCjMmoseCIacwJmqbBXOWSUyWikpiqQGjlquBTdlqMILlSDpmtGKAvxdBEmOzGBooYgKqRFFUDEnDsYRDEReuKlRtQwohQNHXILjKPmCpvbETEJWPFtPGMTGFOcAStyplZIgHkUibZppxrImsUMroSPJhsGvfESCuMwKiJaytOzCexDAk\n" +
                "iMaobxGtMBfCXgmWlHIePwogtPvxRbrTpjLrOPMRgkWYIZDPPeMaXrhmlopLOxQhMarCiiBcSGddAKhTdDZxNVfbVpGoQubBIiKksKUtuysnhcYfsWgwRfZrhfekfwlOUHPdFTGEDEvmEmszjzdLilafJLILmktAUZVVVxyXIpuQBAEVUcdGBjDfgjJCToKlNPJTVMICFzESfimydUqqNoRGugpjjmhEDaruTaEnlbVpBNIqOyxSsUaANxL\n" +
                "zpdzXyZNVmoDLVxTvezcyvSJTEuETBbpjOsPzogIQVuSBkQlnCczogQtGbLAIaHGBvUbJsjYAipGJHfwXhREdGuqOqXCdHTUKfRqYYdMyEwyaVGjulQEKrgTYIhpQLCZNTQeZkikPFBQITKxlQagHnxbbdmWlERXKuQAnklbHubKyvgFZiJuVDSzkwcvHAvEuBDXAnMNFuHriQVvzExtOgVcMDZMlFDWpSpgETgYmziGmdiHRnshgGcjMEl\n" +
                "nnAbZbupFUNYtqpUHJeCPOYnSIakYDmKqFDjPrIBydxioWImnavDxQXABdlWkQnXgPPIrGJjrCDSiPLruQUUCLVpWJSrUmpAVwTZaRESoaOGiQdakPffnBsknBvWypHJsrCnGHBOCyMKfdOhvRFhixHRKOXZazDxtaZtFFikdmHffyXTwsLTqTRVkvIoRiRJLgXzCwsJHztzymVjyySyoANeQnKRvyLyjuWoVRMIPoMCxMgUVBJBYPZrTOQ\n" +
                "EfgTzNxOKNDlIhiOiVcScAIdpQJrkOaFNdAHBvVDWsxFDVuswNnersGPeSixLIKdKUYVDOlkQzrwjGmBzZGAvjKDSIPXThfxOjtcmKLugUSrIauzqangpSLvKMRYZpQkqSCBHOJDpFgHOYcJFnORJUokBIcknQLLMZIUEyCVFyVJcFWCiskHfABvNDkyKkQUWAJCrcFsBecyUeWzKvbNhoxDTuDBTyXfluayRQLEXSkdfmrllbMWTWxADgH\n" +
                "RMQSjIhfNeAjUKSmoCBXNXEaetbWqVqncjFDTjVEzlwDaVrDDuxpcNlLVNyHjWXMbdsxOElZQFKjDETKBzhfYvdNZuTmWZgVtdbFulezZfhvBHonNbsvSNoscHpzNJSrIolENsQnjUbEvpthEBBRkyeVRsxVaZBBCnKxmRWZFRzorzldHEKMmrHpzvHihMZRsJvTmURYlBlLvqOcoDhvegwRBFUOgnicfKozIdCFMKCSiafZkDfGVAPbFXh\n" +
                "SXigpxuRJGHSTPBMNfRbgESqquvPQfdxWcMlnpRMmvcwQIwlLGTEnPZzLGfSNQqhvtAqnRAukQcgkubPfKaVvAwVCTbvulraLTCDfjbYFpuDTjcCTZSnhIVhONaYiyuOBAQenjCYwQLjKQyZZjwLBOoxFIthmVsLBTOZBQkxbGIDiWXKNmPiiwBjbNvRJrMuZWPIAtLdoopEIwlZJLpHxgYXsDRvpuHaKBVSQKBqRjMAAocjmWqLjbLVUAZ\n" +
                "VfaJqfsDCDoGaPuZdzwGMzIARBfwfrOmIiOeXnUIciWJGFBXhlsYKzxpaHmwcBGeNWtKqBMXpShBDSBRSsvuDopoVJVCuypFkPGdeYlcnHIOYBuuizgIKXtseBvizPUwYebkvtndcrIFetHtkVSHxPGbUFpDAFQeEMNYthLnltRccIuGdghKPNqLpLCzcvshQkonlAyIYMuxLCIswiLUsAwgzwOoUKvjtyRmTXOYcfysVNpqRLGUGSVNqxR\n" +
                "CckuWxvbiUzoaxzXokRFBOqWQhoLwTdfqgWARqQPhODZmSqmpDEvEDegoMEgiPQINqMCUKINKyaMiKNoouXDdMIrDOsNWOTbiJhCNGNHtsdtJSLxjJvgYDWdxoQdZuqSHuuvGYbqAislxCYkvWxdnObRDvfFnhnzoXAOcLtePsbxDOerSPjObEnbnpVzielSjpSqsFQecdYgpWjfQMIgShckerDcunknCSTnYunzRuwuLieClvnlQOymmHP\n" +
                "ehcUqiZmpDSReorDAetcSursRNcTSYofZeqcjSglSRHGOMoFKcMSVCufugIAxRDKFwJNYVerntGczOEhGJGRaqJYklmShukVAmHNVVDEwCVzjWPgHZdqkVSYVAeGrRJAkglXcrbGFboOSqSJCvHBYnrIPrtKVPIuTGGVmMlcpXcCVyKQCIMqJtolHikWGMEgcOsdNjmxvseUcinqOpjtVkmwtAlHSxxwdWcIBADNhadHubuanNypgvLGnTq\n" +
                "rnQAlMHdaxGEGFMvHscPrviuUcfNhVaNotrxnmCMmLUkntOcEwScoJlTaKgkensKretPfvMkqKeCoyDNTvbCjWuWlHvxTLoHRbgQKHTJiGYeZIMbZRxHzVYKOzntuJPEBKxOqqEBhiGVKmznaeWHvsKWiyxZSFophKeSLruZuAsdPvqWQYyTuPlCMnabFTWVFNEFvXHWQuUsoLTueFfdWyVaYksdCUbNUCzgblPSpsJmicmlqOcKOfDvOwh\n" +
                "YjkZONacYSzzCfXYhvPKcRUizQfyTJQNgcglmjlqLsOrFeRkcsxoBLPnaJKjhuiwQRhmMMgDwLxosigqduzYQAdghRSeSmBBhIeejgdQgTrUHrIcrRknecHFcFOwQAghclFteuWRmnQypXTnmBFJfWbQDnNYjqMUpaLFOYOYqUgfCbGdxSdhjPydQFdwPTjNZggYMqkStlNecTVAITaTfucrvVGBUNqWGYOLSwtMtCIOthsqqtAPbnsqYAk\n" +
                "WAfLpCIJoFucTzcCtVgEweQApmWBVGgPYcHTcejOlXVDRFihaWAyKEHWCSxdHnffWyyxWaEupRowfkXwrrJlZNXddUYmwhbvtgDfTNzrUqfsyWlbBDFYgHXwcMLUHBRvqogUDqxYyNOOQgqpjGRzJxWOElLHhDCYBzZymgalYwGyeCMRwaugJSEnkBaCTPnYQaiVpNuLvVtfKsSubcJowMamNcsZwbZIWPUbsyZykWNgcsBGXshKOLiwrNS\n" +
                "TKSYnZEBUiEmNZBsYLGlfUBbOdbWUQVysWToYgSPGvEmkyQxkDWVJhrtnbUUBFoVLMvZmloGcwxNaqGOtbNDTnTQWrlXbMtIjPhfaSOgfdXfnekleclZZNQtPXdjTxIVzOKZXBqwEKaAqtZtDkwuXPreLROpaOKBvpjzCuETvgdsOrkKnmIhGzNaMDJXZpaaXIQGFsqVNpXgLWKMiEwQkDyJSajKmPMKtYloUHhvMRbwLolBnDipRvVzxog";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", firstName);
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, responseCreateAuth.asString());
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    @Step("Request date not same user")
    @Test
    public void testRequestDateOfNotSameUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/1")
                .andReturn();

        System.out.println(responseUserData.asString());

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }

    @Step("Changing data of user without of auyhorization")
    @Test
    public void testChangeDataOfUserNoAuthorization() {
        String newName = "Changed name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditName = RestAssured
                .given()
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/1")
                .andReturn();

        System.out.println(responseEditName.asString());
        Assertions.assertResponseTextEquals(responseEditName, "{\"error\":\"Auth token not supplied\"}");
    }

    @Step("Changing data of user by login for another user")
    @Test
    public void testChangeDataOfUserWithAnotherAuthorization() {
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        int userId = responseCreateAuth.getInt("id");

        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String newName = "Changed name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditName = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + (userId-1))
                .andReturn();

        System.out.println(responseEditName.asString());
        Assertions.assertResponseTextEquals(responseEditName, "{\"error\":\"This user can only edit their own data.\"}");
    }

    @Step("Changing date of user for incorrect email")
    @Test
    public void testChangeEmailForIncorrect() {
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseCreateAuth.getString("id");


        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();


        String newEmail = "Changedemailmail.ru";
        Map<String, String> editData = new HashMap<>();
        editData.put("email", newEmail);

        Response responseEditEmail = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        System.out.println(responseEditEmail.asString());
        Assertions.assertResponseTextEquals(responseEditEmail, "{\"error\":\"Invalid email format\"}");
    }

    @Step("Change first name of User for incorrect")
    @Test
    public void testChangeNameOfUserForIncorrect() {
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseCreateAuth.getString("id");

        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();


        String newName = "M";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditName = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        System.out.println(responseEditName.asString());
        Assertions.assertResponseTextEquals(responseEditName, "{\"error\":\"The value for field `firstName` is too short\"}");
    }

    String cookie;
    String header;
    Integer userIdOnAuth;

    @Step("Try to delete locked user")
    @Test
    public void testDeleteLockUser() {
        Map<String, String> authorizationDate = new HashMap<>();
        authorizationDate.put("email", "vinkotov@example.com");
        authorizationDate.put("password", "1234");

        Response responseAuthorization = RestAssured
                .given()
                .body(authorizationDate)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        this.cookie = this.getCookie(responseAuthorization, "auth_sid");
        this.header = this.getHeader(responseAuthorization, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseAuthorization, "user_id");

        Response responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", this.header)
                .cookie("auth_sid", this.cookie)
                .delete("https://playground.learnqa.ru/api/user/" + userIdOnAuth)
                .andReturn();

        System.out.println(responseCheckAuth.asString());
        Assertions.assertResponseTextEquals(responseCheckAuth, "{\"error\":\"Please, do not delete test users with ID 1, 2, 3, 4 or 5.\"}");
    }

    @Step("Delete current user")
    @Test
    public void testDeleteCurrentUser() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Delete
        Response responseDeleteUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        //Get
        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        System.out.println(responseUserData.asString());
        Assertions.assertResponseTextEquals(responseUserData, "User not found");
    }

    @Step("Delete user with another login")
    @Test
    public void testDeleteUserWithAnotherLogin() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseCreateAuth.getString("id");

        //Login with another user
        Map<String, String> authorizationDate = new HashMap<>();
        authorizationDate.put("email", "vinkotov@example.com");
        authorizationDate.put("password", "1234");

        Response responseAuthorization = RestAssured
                .given()
                .body(authorizationDate)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Delete
        Response responseDeleteUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseAuthorization, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseAuthorization, "auth_sid"))
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        System.out.println(responseDeleteUser.asString());
        Assertions.assertResponseTextEquals(responseDeleteUser, "{\"error\":\"Please, do not delete test users with ID 1, 2, 3, 4 or 5.\"}");
    }
}
