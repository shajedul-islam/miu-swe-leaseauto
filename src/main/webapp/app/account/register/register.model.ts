export class Registration {
  constructor(
    public login: string,
    public email: string,
    public password: string,
    public langKey: string,
    public name: string, // New field
    public phoneNumber: string, // New field
    public drivingLicenseCredentials: string // New field
  ) {}
}
